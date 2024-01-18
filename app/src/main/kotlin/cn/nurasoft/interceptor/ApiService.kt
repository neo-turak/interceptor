package cn.nurasoft.interceptor

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("ghapi")
    suspend fun getTokoto(
        @Query("type") token: String = "query",
        @Query("n") n: String = "new"
    ): TokoToModel
}
