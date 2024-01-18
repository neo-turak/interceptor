package cn.nurasoft.interceptor

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("articles")
    @FormUrlEncoded
    suspend fun getArticles(
        @Field("title") title: String,
        @Field("body") body: String
    ): Response<HttpResponse<ArticleModel>>
}
