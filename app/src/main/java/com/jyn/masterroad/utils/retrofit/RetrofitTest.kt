package com.jyn.masterroad.utils.retrofit

import com.apkfuns.logutils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jiaoyaning on 2021/8/3.
 */
class RetrofitTest {
    companion object {
        const val TAG = "Retrofit"

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService: RetrofitService by lazy { retrofit.create(RetrofitService::class.java) }
    }

    fun getArticleList() {
        retrofitService
            .getArticleList(0)
            .enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    LogUtils.tag(TAG).i("onResponse response:$response")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    LogUtils.tag(TAG).i("onFailure response:$t")
                }
            })
    }
}