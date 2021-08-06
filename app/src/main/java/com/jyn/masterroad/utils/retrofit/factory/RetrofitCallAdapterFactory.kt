package com.jyn.masterroad.utils.retrofit.factory

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class RetrofitCallAdapterFactory : CallAdapter.Factory() {

    /*
     * Factory的作用就是根据你定义的接口的返回值类型来判断是否是该CallAdapter需要处理的返回值类型，
     * 如果是，则返回一个处理该返回值类型的CallAdapter，如果不是，则返回null。
     *
     * 比如如果返回值是Observable时，可以判断出是rxjava CallAdapter需要处理的类型
     */

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return null
    }
}