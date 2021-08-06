package com.jyn.masterroad.utils.retrofit.factory

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RetrofitCallAdapter<R> : CallAdapter<R, Any> {
    override fun responseType(): Type? {
        return null
    }

    override fun adapt(call: Call<R>): Any? {
        return null
    }
}