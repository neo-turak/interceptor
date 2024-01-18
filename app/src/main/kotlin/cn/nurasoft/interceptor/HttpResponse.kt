package cn.nurasoft.interceptor

data class HttpResponse<T>(
    val msg: String,
    val code: Int,
    val data: T
)