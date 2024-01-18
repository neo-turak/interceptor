package cn.nurasoft.interceptor

import androidx.annotation.Keep

@Keep

data class TokoToModel(
    val code: Int, // 200
    val data: Data,
    val msg: String, // OK
    val time: Int // 1665426093
)  {
    @Keep
    data class Data(
        val content: String, // 我爱的人也爱着我，对我来说这简直是个奇迹。
        val creator: String, // nana酱
        val from: String, // NANA
        val type: String // b
    )
}