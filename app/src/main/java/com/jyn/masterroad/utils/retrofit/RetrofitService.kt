package com.jyn.masterroad.utils.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    companion object {
        /*
         * addCallAdapterFactory影响的就是第一部分的Call或者Observable，
         * Call类型是默认支持的(内部由DefaultCallAdapterFactory支持)，而如果要支持Observable，我们就需要自己添加
         * `addCallAdapterFactory(RxJavaCallAdapterFactory.create())`
         */
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
    }

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Call<Any>

    @GET("article/list/{page}/json")
    suspend fun getArticleList3(@Path("page") page: Int): Any
}