package cn.nurasoft.request

import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.zip.*

/**
 * ================================================
 * 处理压缩和解压的工具类
 * ================================================
 */
class ZipHelper
private constructor() {
    companion object {
        /**
         * zlib decompress 2 String
         *
         * @param bytesToDecompress bytesToDecompress
         * @param charsetName charsetName
         * @return String
         */
        @JvmOverloads
        fun decompressToStringForZlib(
            bytesToDecompress: ByteArray,
            charsetName: Charset = Charsets.UTF_8
        ): String? {
            val bytesDecompressed = decompressForZlib(
                bytesToDecompress
            )
            var returnValue: String? = null
            try {
                returnValue = String(
                    bytesDecompressed!!,
                    0,
                    bytesDecompressed.size,
                    charsetName
                )
            } catch (uee: UnsupportedEncodingException) {
                uee.printStackTrace()
            }
            return returnValue
        }

        /**
         * zlib decompress 2 byte
         *
         * @param bytesToDecompress
         * @return
         */
        fun decompressForZlib(bytesToDecompress: ByteArray): ByteArray? {
            var returnValues: ByteArray? = null
            val inflater = Inflater()
            val numberOfBytesToDecompress = bytesToDecompress.size
            inflater.setInput(
                bytesToDecompress,
                0,
                numberOfBytesToDecompress
            )
            var numberOfBytesDecompressedSoFar = 0
            val bytesDecompressedSoFar: MutableList<Byte> = ArrayList()
            try {
                while (!inflater.needsInput()) {
                    val bytesDecompressedBuffer = ByteArray(numberOfBytesToDecompress)
                    val numberOfBytesDecompressedThisTime = inflater.inflate(
                        bytesDecompressedBuffer
                    )
                    numberOfBytesDecompressedSoFar += numberOfBytesDecompressedThisTime
                    for (b in 0 until numberOfBytesDecompressedThisTime) {
                        bytesDecompressedSoFar.add(bytesDecompressedBuffer[b])
                    }
                }
                returnValues = ByteArray(bytesDecompressedSoFar.size)
                for (b in returnValues.indices) {
                    returnValues[b] = bytesDecompressedSoFar[b]
                }
            } catch (dfe: DataFormatException) {
                dfe.printStackTrace()
            }
            inflater.end()
            return returnValues
        }

        /**
         * zlib compress 2 byte
         *
         * @param bytesToCompress
         * @return
         */
        fun compressForZlib(bytesToCompress: ByteArray?): ByteArray {
            val deflater = Deflater()
            deflater.setInput(bytesToCompress)
            deflater.finish()
            val bytesCompressed = ByteArray(Short.MAX_VALUE.toInt())
            val numberOfBytesAfterCompression = deflater.deflate(bytesCompressed)
            val returnValues = ByteArray(numberOfBytesAfterCompression)
            System.arraycopy(
                bytesCompressed,
                0,
                returnValues,
                0,
                numberOfBytesAfterCompression
            )
            return returnValues
        }

        /**
         * zlib compress 2 byte
         *
         * @param stringToCompress
         * @return
         */
        fun compressForZlib(stringToCompress: String): ByteArray? {
            var returnValues: ByteArray? = null
            try {
                returnValues =
                    compressForZlib(
                        stringToCompress.toByteArray(StandardCharsets.UTF_8)
                    )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return returnValues
        }

        /**
         * gzip compress 2 byte
         *
         * @param string
         * @return
         * @throws IOException
         */
        fun compressForGzip(string: String): ByteArray? {
            var os: ByteArrayOutputStream? = null
            var gos: GZIPOutputStream? = null
            try {
                os = ByteArrayOutputStream(string.length)
                gos = GZIPOutputStream(os)
                gos.write(string.toByteArray(StandardCharsets.UTF_8))
                return os.toByteArray()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                closeQuietly(gos)
                closeQuietly(os)
            }
            return null
        }
        /**
         * gzip decompress 2 string
         *
         * @param compressed
         * @param charsetName
         * @return
         */
        /**
         * gzip decompress 2 string
         *
         * @param compressed
         * @return
         * @throws IOException
         */
        @JvmOverloads
        fun decompressForGzip(compressed: ByteArray, charsetName: Charset = Charsets.UTF_8): String? {
            val bufferSize = compressed.size
            var gis: GZIPInputStream? = null
            var bis: ByteArrayInputStream? = null
            try {
                bis = ByteArrayInputStream(compressed)
                gis = GZIPInputStream(bis, bufferSize)
                val string = StringBuilder()
                val data = ByteArray(bufferSize)
                var bytesRead: Int
                while (gis.read(data).also { bytesRead = it } != -1) {
                    string.append(String(data, 0, bytesRead, charsetName))
                }
                return string.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                closeQuietly(gis)
                closeQuietly(bis)
            }
            return null
        }

        fun closeQuietly(closeable: Closeable?) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (rethrown: RuntimeException) {
                    throw rethrown
                } catch (ignored: Exception) {
                }
            }
        }
    }
}