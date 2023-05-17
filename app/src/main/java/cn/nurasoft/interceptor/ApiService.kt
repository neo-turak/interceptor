package cn.nurasoft.interceptor

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/hitokoto")
    suspend fun getTokoto(@Query("token") token: String = "FtTtwONEZmM"): TokoToModel
}
