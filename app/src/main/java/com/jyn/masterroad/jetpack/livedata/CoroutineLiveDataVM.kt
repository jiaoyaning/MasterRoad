package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import androidx.lifecycle.*
import com.jyn.common.Base.BaseVM
import kotlinx.coroutines.delay

/**
 * 将协程与 LiveData 一起使用
 * https://developer.android.google.cn/topic/libraries/architecture/coroutines#livedata
 *
 * 学习采用 Kotlin Flow 和 LiveData 的高级协程
 * https://developer.android.com/codelabs/advanced-kotlin-coroutines#0
 */
class CoroutineLiveDataVM(application: Application) : BaseVM(application) {
    val user: LiveData<Int> = liveData {
        delay(2000) //获取数据

        /**
         * emit() 发送数据
         */
        emit(1)
        /**
         * emitSource() 发送新的数据源LiveData
         */
        emitSource(MutableLiveData(1))
    }

    init {
        user.observe(this, Observer {

        })
    }
}