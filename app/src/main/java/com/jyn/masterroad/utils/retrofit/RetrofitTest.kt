package com.jyn.masterroad.utils.retrofit

import retrofit2.Retrofit

/**
 * Created by jiaoyaning on 2021/8/3.
 */
class RetrofitTest {
    companion object {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .build()

        val retrofitService: RetrofitService by lazy {
            retrofit.create(RetrofitService::class.java)
        }
    }

    fun getArticleList() {
        retrofitService.getArticleList(0)
    }
}