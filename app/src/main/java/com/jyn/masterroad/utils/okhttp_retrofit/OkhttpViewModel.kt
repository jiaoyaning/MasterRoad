package com.jyn.masterroad.utils.okhttp_retrofit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by jiaoyaning on 2021/4/22.
 */
class OkhttpViewModel(application: Application) : AndroidViewModel(application) {

    var response: MutableLiveData<String> = MutableLiveData()

    companion object {
        const val WXARTICLE = "https://www.wanandroid.com/wxarticle/chapters/json"
    }

    // 或者
    private val client: OkHttpClient by lazy {
        OkHttpClient
                .Builder()
                .addInterceptor(LoggingInterceptor())
                .build()
    }

    /**
     * 同步请求
     */
    public fun execute() {
        val request = Request.Builder()
                .url(WXARTICLE)
                .build()

        this.response.value = client.newCall(request).execute().body.toString()
    }

    /**
     * 异步请求
     */
    public fun enqueue() {

    }
}