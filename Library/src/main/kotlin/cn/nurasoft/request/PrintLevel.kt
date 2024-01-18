package cn.nurasoft.request

/**
 * @author 努尔江
 * Created on: 2023/5/15
 * @project Interceptor
 * Description:
 **/

enum class PrintLevel {
    /**
     * 不打印log
     */
    NONE,

    /**
     * 只打印请求信息
     */
    REQUEST,

    /**
     * 只打印响应信息
     */
    RESPONSE,

    /**
     * 所有数据全部打印
     */
    ALL
}