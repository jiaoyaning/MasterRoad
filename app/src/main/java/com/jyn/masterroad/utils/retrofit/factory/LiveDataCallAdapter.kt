package com.jyn.masterroad.utils.retrofit.factory

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(var callReturnType: Type) : CallAdapter<R, LiveData<R>> {

    /**
     * 此处type为泛型的类型，如Observable<BaseBean>中的 BaseBean
     * 返回后交给Converter去解析成对象
     */
    override fun responseType(): Type {
        return callReturnType
    }

    /*
     * Call对象可以完成同步或异步的网络请求。Retrofit把这样的一个Call对象传递给adapt方法，
     * 然后不同的CallAdapter可以将这个Call对象包装成你需要返回给用户使用的T对象，T对象的内部是使用Call对象的网络请求的能力。
     *
     * 比如像Rxjava,我们需要让用户在定义网络接口的时候直接返回一个Observable对象。
     * 这样我们就可以定义一个RxJavaCallAdapter来将Call对象包装成Observable对象。
     */
    override fun adapt(call: Call<R>): LiveData<R> {
        return object : LiveData<R>() {
            //防止多次设置
            private val started = AtomicBoolean(false)

            //只有活跃的时候才修改
            override fun onActive() {
                if (!started.compareAndSet(false, true)) return

                call.enqueue(object : Callback<R> {
                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(response.body())
                    }

                    override fun onFailure(call: Call<R>, t: Throwable) {
                        //抛出异常
                        if (call.isCanceled) return
                        throw t
                    }
                })
            }
        }
    }
}