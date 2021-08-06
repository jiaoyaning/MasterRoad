package com.jyn.masterroad.utils.okhttp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.utils.okhttp.interceptor.LoggingInterceptor
import okhttp3.*
import java.io.IOException

/*
 * 从设计模式看OkHttp源码 TODO
 * https://mp.weixin.qq.com/s/eHLXxjvMgII6c_FVRwwdjg
 *
 * OKHttp源码解析(一)--初阶
 * https://www.jianshu.com/p/82f74db14a18?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 *
 * OKHttp3--Dispatcher分发器实现同步异步请求源码解析【三】
 * https://blog.csdn.net/qq_30993595/article/details/86681210
 *
 * 随机诗词: http://www.alapi.cn/api/view/8
 */
class OkhttpViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val TAG = "Okhttp"

        const val WAN_ANDROID = "https://www.wanandroid.com/wxarticle/chapters/json"

        val okHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                .build()
        }
    }

    /**
     * 同步请求
     */
    fun execute() {
        val request = Request.Builder()
            .url(WAN_ANDROID)
            .build()

        Thread {
            val response = okHttpClient.newCall(request).execute()
            LogUtils.tag(TAG).i("同步请求 execute response:$response")
        }.start()
    }

    /**
     * 异步请求
     */
    fun enqueue() {
        val request = Request.Builder()
            .url(WAN_ANDROID)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtils.tag(TAG).i("异步请求 enqueue onFailure call:$call")
            }

            override fun onResponse(call: Call, response: Response) {
                LogUtils.tag(TAG).i("异步请求 enqueue onResponse response:$response")
            }
        })
    }
}