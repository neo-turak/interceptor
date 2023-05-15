package cn.nurasoft.library

import android.util.Log
import cn.nurasoft.library.CharacterHandler.Companion.jsonFormat
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * 解析框架中的网络请求和响应结果,并以日志形式输出,调试神器
 */
class RequestInterceptor : Interceptor {
    private var mPrinter: FormatPrinter = DefaultFormatPrinter()
    private var printLevel = PrintLevel.ALL
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val logRequest =
            printLevel == PrintLevel.ALL || printLevel != PrintLevel.NONE && printLevel == PrintLevel.REQUEST
        if (logRequest) {
            //打印请求信息
            if (request.body != null && isParseable(request.body!!.contentType())) {
                mPrinter.printJsonRequest(request, parseParams(request))
            } else {
                mPrinter.printFileRequest(request)
            }
        }
        val logResponse =
            printLevel == PrintLevel.ALL || printLevel != PrintLevel.NONE && printLevel == PrintLevel.RESPONSE
        val t1 = if (logResponse) System.nanoTime() else 0
        val originalResponse: Response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            Log.w("Http Error: %s", e)
            throw e
        }
        val t2 = if (logResponse) System.nanoTime() else 0
        val responseBody = originalResponse.body

        //打印响应结果
        var bodyString: String? = null
        if (responseBody != null && isParseable(responseBody.contentType())) {
            bodyString = printResult(request, originalResponse, logResponse)
        }
        if (logResponse) {
            val segmentList: List<String?> = request.url.encodedPathSegments
            val header = originalResponse.headers.toString()
            val code = originalResponse.code
            val isSuccessful = originalResponse.isSuccessful
            val message = originalResponse.message
            val url = originalResponse.request.url.toString()
            if (responseBody != null && isParseable(responseBody.contentType())) {
                mPrinter.printJsonResponse(
                    TimeUnit.NANOSECONDS.toMillis(t2 - t1), isSuccessful,
                    code, header, responseBody.contentType(), bodyString, segmentList, message, url
                )
            } else {
                mPrinter.printFileResponse(
                    TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                    isSuccessful, code, header, segmentList, message, url
                )
            }
        }
        return originalResponse
    }

    /**
     * 打印响应结果
     *
     * @param request     [Request]
     * @param response    [Response]
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun printResult(request: Request, response: Response, logResponse: Boolean): String? {
        return try {
            //读取服务器返回的结果
            val responseBody = response.newBuilder().build().body
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            //获取content的压缩类型
            val encoding = response
                .headers["Content-Encoding"]
            val clone = buffer.clone()

            //解析response content
            parseContent(responseBody, encoding, clone)
        } catch (e: IOException) {
            e.printStackTrace()
            "{\"error\": \"" + e.message + "\"}"
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody [ResponseBody]
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private fun parseContent(
        responseBody: ResponseBody?,
        encoding: String?,
        clone: Buffer
    ): String? {
        var charset:Charset = StandardCharsets.UTF_8
        val contentType = responseBody!!.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)!!
        }
        //content 使用 gzip 压缩
        return if ("gzip".equals(encoding, ignoreCase = true)) {
            //解压
            ZipHelper.decompressForGzip(
                clone.readByteArray(),
                convertCharset(charset)
            )
        } else if ("zlib".equals(encoding, ignoreCase = true)) {
            //content 使用 zlib 压缩
            ZipHelper.decompressToStringForZlib(
                clone.readByteArray(),
                convertCharset(charset)
            )
        } else {
            //content 没有被压缩, 或者使用其他未知压缩方式
            clone.readString(charset!!)
        }
    }



    companion object {
        /**
         * 解析请求服务器的请求参数
         *
         * @param request [Request]
         * @return 解析后的请求信息
         */
        fun parseParams(request: Request): String {
            return try {
                val body = request.newBuilder().build().body ?: return ""
                val mBuffer = Buffer()
                body.writeTo(mBuffer)
                var charset = StandardCharsets.UTF_8
                val contentType = body.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                var json: String? = mBuffer.readString(charset!!)
                if (UrlEncoderUtils.hasUrlEncoded(json)) {
                    json = URLDecoder.decode(json, convertCharset(charset))
                }
                jsonFormat(json!!)
            } catch (e: IOException) {
                e.printStackTrace()
                "{\"error\": \"" + e.message + "\"}"
            }
        }

        /**
         * 是否可以解析
         *
         * @param mediaType [MediaType]
         * @return `true` 为可以解析
         */
        fun isParseable(mediaType: MediaType?): Boolean {
            return if (mediaType?.type == null) {
                false
            } else (isText(mediaType) || isPlain(
                mediaType
            )
                    || isJson(mediaType) || isForm(
                mediaType
            )
                    || isHtml(mediaType) || isXml(
                mediaType
            ))
        }

        fun isText(mediaType: MediaType?): Boolean {
            return if (mediaType?.type == null) {
                false
            } else "text" == mediaType.type
        }

        fun isPlain(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype == null) {
                false
            } else mediaType.subtype.lowercase(Locale.getDefault()).contains("plain")
        }

        @JvmStatic
        fun isJson(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype == null) {
                false
            } else mediaType.subtype.lowercase(Locale.getDefault()).contains("json")
        }

        @JvmStatic
        fun isXml(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype == null) {
                false
            } else mediaType.subtype.lowercase(Locale.getDefault())
                .contains("xml")
        }

        fun isHtml(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype == null) {
                false
            } else mediaType.subtype.lowercase(
                Locale.getDefault()
            ).contains("html")
        }

        fun isForm(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype == null) {
                false
            } else mediaType.subtype.lowercase(
                Locale.getDefault()
            ).contains("x-www-form-urlencoded")
        }

        fun convertCharset(charset: Charset?): String {
            val s = charset.toString()
            val i = s.indexOf("[")
            return if (i == -1) {
                s
            } else s.substring(i + 1, s.length - 1)
        }
    }
}