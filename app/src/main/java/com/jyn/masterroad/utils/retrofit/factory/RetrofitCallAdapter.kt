package com.jyn.masterroad.utils.retrofit.factory

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RetrofitCallAdapter<R> : CallAdapter<R, Any> {
    override fun responseType(): Type? {
        return null
    }

    /*
     * Call对象可以完成同步或异步的网络请求。Retrofit把这样的一个Call对象传递给adapt方法，
     * 然后不同的CallAdapter可以将这个Call对象包装成你需要返回给用户使用的T对象，T对象的内部是使用Call对象的网络请求的能力。
     *
     * 比如像Rxjava,我们需要让用户在定义网络接口的时候直接返回一个Observable对象。
     * 这样我们就可以定义一个RxJavaCallAdapter来将Call对象包装成Observable对象。
     */
    override fun adapt(call: Call<R>): Any? {
        return null
    }
}