package cn.nurasoft.request

/**
 * @author 努尔江
 * Created on: 2023/5/15
 * @project Interceptor
 * Description:
 **/

enum class HeaderLogLevel {
    /**
     * 通过 {[android.util.Log.v]} 来打印
     */
    LOG_V,
    /**
     * 通过 {[android.util.Log.d]} 来打印
     */
    LOG_D,
    /**
     * 通过 {[android.util.Log.i]} 来打印
     */
    LOG_I,
    /**
     * 通过 {[android.util.Log.w]} 来打印
     */
    LOG_W,
    /**
     * 通过 {[android.util.Log.e]} 来打印
     */
    LOG_E
}