package com.jyn.masterroad.kotlin.flow

import android.app.Application
import androidx.lifecycle.asLiveData
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateAndSharedFlow(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Flow"
    }

    /*
     * StateFlow 和 SharedFlow 是热流，在垃圾回收之前，都是存在内存之中，并且处于活跃状态的。
     * 可以将已发送过的数据发送给新的订阅者
     */

    //region 一、StateFlow
    private val state = MutableStateFlow("初始值")

    /*
     * 那么 StateFlow 和 LiveData 有什么区别吗？
     *    1. StateFlow 必须有初始值，LiveData 不需要。
     *    2. 当 View 变为 STOPPED 状态时，LiveData.observe() 会自动取消注册使用方，
     *       而从 StateFlow 或任何其他数据流收集数据则不会取消注册使用方。
     */

    //订阅
    @InternalCoroutinesApi
    fun stateFlowCollect() = mainScope.launch {
        LogUtils.tag(TAG).i("stateFlowCollect -> 订阅")
        state.collect {
            LogUtils.tag(TAG).i("StateFlowTest -> emit：$it")
        }
    }

    //调用
    @InternalCoroutinesApi
    fun stateFlowTest() = mainScope.launch {
        List(10) {
            state.value = it.toString()
            delay(100)
        }
    }
    //endregion

    //region 二、SharedFlow
    private val sharedFlow = MutableSharedFlow<Int>(
            5,             // 参数一：当新的订阅者Collect时，发送几个已经发送过的数据给它
            3, // 参数二：减去replay，MutableSharedFlow还缓存多少数据
            BufferOverflow.SUSPEND // 参数三：缓存策略，三种:1.DROP_LATEST丢掉最新值；2.DROP_OLDEST丢掉最旧值；3.SUSPEND挂起
    )

    //订阅
    fun sharedFlowCollect() = mainScope.launch {
        LogUtils.tag(TAG).i("sharedFlowCollect -> 订阅")
        sharedFlow.collect {
            LogUtils.tag(TAG).i("SharedFlow -> emit：$it")
        }
    }

    /*
     * 当 MutableSharedFlow 中缓存数据量超过阈值时，emit 方法和 tryEmit 方法的处理方式会有不同：
     *     1. emit 方法：当缓存策略为 BufferOverflow.SUSPEND 时，emit 方法会挂起，直到有新的缓存空间。
     *     2. tryEmit 方法：tryEmit 会返回一个 Boolean 值，true 代表传递成功，false 代表会产生一个回调，让这次数据发射挂起，直到有新的缓存空间。
     */

    //调用
    fun sharedFlowTry() = mainScope.launch {
        List(10) { sharedFlow.emit(it) }
    }

    fun sharedFlowTryEmit() = mainScope.launch {
        List(10) {
            val tryEmit = sharedFlow.tryEmit(it)
            LogUtils.tag(TAG).i("sharedFlowTryEmit -> tryEmit：$tryEmit")
        }
    }

    //endregion
}