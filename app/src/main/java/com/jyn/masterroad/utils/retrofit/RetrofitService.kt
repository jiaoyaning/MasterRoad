package com.jyn.masterroad.utils.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

public interface RetrofitService {
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Call<Any>
}