package com.jyn.masterroad.kotlin.flow

import android.app.Application
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 * 协程进阶技巧 - StateFlow和SharedFlow
 * https://juejin.cn/post/6937138168474894343
 */
class StateAndSharedFlow(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Flow"
    }

    /*
     * StateFlow 和 SharedFlow 是热流，在垃圾回收之前，都是存在内存之中，并且处于活跃状态的。可以将已发送过的数据发送给新的订阅者
     * StateFlow 和 SharedFlow 提供了在 Flow 中使用 LiveData 式更新数据的能力，但是如果要在 UI 层使用，需要注意生命周期的问题。
     * StateFlow 和 SharedFlow 相比，StateFlow 需要提供初始值，SharedFlow 配置灵活，可提供旧数据同步和缓存配置的功能。
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

    // emit
    fun sharedFlowTry() = mainScope.launch {
        List(10) { sharedFlow.emit(it) }
    }

    // tryEmit
    fun sharedFlowTryEmit() = mainScope.launch {
        List(10) {
            val tryEmit = sharedFlow.tryEmit(it)
            LogUtils.tag(TAG).i("sharedFlowTryEmit -> tryEmit：$tryEmit")
        }
    }

    // 扩展方法 shareIn
    fun flowShareIn() = mainScope.launch {

        /*
         * SharingStarted.WhileSubscribed() 存在订阅者时，将使上游提供方保持活跃状态。
         * SharingStarted.Eagerly           立即启动提供方。
         * SharingStarted.Lazily            在第一个订阅者出现后开始共享数据，并使数据流永远保持活跃状态。
         */

        flow {
            (1..10).forEach {
                emit(it)
                delay(100)
            }
        }.shareIn(
                this,
                replay = 1,
                started = SharingStarted.WhileSubscribed() // 启动政策
        ).collect {
            LogUtils.tag(TAG).i("shareIn -> collect：$it")
        }
    }
    //endregion
}