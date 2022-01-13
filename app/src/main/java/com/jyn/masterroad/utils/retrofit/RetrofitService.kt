package com.jyn.masterroad.utils.retrofit

import androidx.lifecycle.LiveData
import com.jyn.masterroad.utils.retrofit.factory.LiveDataCallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
    }

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Call<Any>

    @GET("article/list/{page}/json")
    fun getArticleList2(@Path("page") page: Int): Observable<Any>

    @GET("article/list/{page}/json")
    suspend fun getArticleList3(@Path("page") page: Int): Any

    @GET("article/list/{page}/json")
    suspend fun getArticleList5(@Path("page") page: Int): Response<Any> //同步请求

    @GET("article/list/{page}/json")
    fun getArticleList4(@Path("page") page: Int): LiveData<Any>
}