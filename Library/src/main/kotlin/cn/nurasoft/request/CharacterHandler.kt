package cn.nurasoft.request

import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * ================================================
 * 处理字符串的工具类
 * ================================================
 */
class CharacterHandler
private constructor() {
    companion object {

        /**
         * json 格式化
         *
         * @param targetJson target for format.
         * @return the formatted json string.
         */

        fun jsonFormat(targetJson: String): String {
            var json = targetJson
            if (TextUtils.isEmpty(json)) {
                return "Empty/Null json content"
            }
            var message: String
            try {
                json = json.trim { it <= ' ' }
                message = if (json.startsWith("{")) {
                    val jsonObject = JSONObject(json)
                    jsonObject.toString(4)
                } else if (json.startsWith("[")) {
                    val jsonArray = JSONArray(json)
                    jsonArray.toString(4)
                } else {
                    formFormat(json)
                }
            } catch (e: JSONException) {
                message = json
            } catch (error: OutOfMemoryError) {
                message = "Output omitted because of Object size"
            }
            return message
        }

        fun formatString(str: String): String {
            return if (isJson(str)) {
                jsonFormat(str)
            } else {
                formFormat(str)
            }
        }


        private fun isJson(str: String): Boolean {
            return try {
                JSONObject(str)
                true
            } catch (e: Exception) {
                false
            }
        }


        private fun formFormat(target: String): String {
            var message: String
            val countEqual = target.count { it == '=' }
            if (countEqual == 1) {
                val start = "****${target.substringBefore("=")}****\n"
                Log.d("START", start)
                val end = target.substringAfter("=")
                if (isJson(end)) {
                    message = jsonFormat(end)
                } else {
                    message = start + end.replace("&", "\n")
                }
            } else {
                message = target.replace("&", "\n")
            }
            message = message.plus("\n")
            return message
        }

        /**
         * xml 格式化
         *
         * @param xml the target for format
         * @return  the formatted string.
         */

        fun xmlFormat(xml: String?): String? {
            if (TextUtils.isEmpty(xml)) {
                return "Empty/Null xml content"
            }
            val message: String? = try {
                val xmlInput: Source = StreamSource(StringReader(xml))
                val xmlOutput = StreamResult(StringWriter())
                val transformer = TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transformer.transform(xmlInput, xmlOutput)
                xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
            } catch (e: TransformerException) {
                xml
            }
            return message
        }
    }
}