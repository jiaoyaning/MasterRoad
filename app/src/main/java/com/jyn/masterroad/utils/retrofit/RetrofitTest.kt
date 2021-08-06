package com.jyn.masterroad.utils.retrofit

import com.apkfuns.logutils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Retrofit源码解析---addConverterFactory和addCallAdapterFactory区别
 * https://blog.csdn.net/new_abc/article/details/53021387
 */
class RetrofitTest {
    companion object {
        const val TAG = "Retrofit"

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