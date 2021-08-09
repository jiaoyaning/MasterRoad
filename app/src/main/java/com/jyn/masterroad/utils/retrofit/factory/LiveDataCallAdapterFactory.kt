package com.jyn.masterroad.utils.retrofit.factory

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/*
 * Retrofit 结合 Lifecycle, 将 Http 生命周期管理到极致
 * https://mp.weixin.qq.com/s/omCnmMX3XVq-vnKoDnQXTg
 *
 * Retrofit2.0的使用之自定义CallAdapter.Factory
 * https://blog.csdn.net/qq_36046305/article/details/77917597
 *
 * LiveData+Retrofit网络请求实战
 * https://www.jianshu.com/p/34fb6ffaa684
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    companion object {
        const val TAG = "Retrofit"

        fun create() = LiveDataCallAdapterFactory()
    }

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
        //获取returnType的原始类型  如 Observable<R>
        val rawType = getRawType(returnType)
        //返回值的类型必须是 LiveData<>  并且带有泛型
        if (rawType == LiveData::class.java && returnType is ParameterizedType) {
            //获取returnType泛型的类型  如Observable<BaseBean>中的 BaseBean
            val callReturnType = getParameterUpperBound(0, returnType)
            //使用 RetrofitCallAdapter 转换处理数据
            return LiveDataCallAdapter<Any>(callReturnType)
        }
        return null
    }
}