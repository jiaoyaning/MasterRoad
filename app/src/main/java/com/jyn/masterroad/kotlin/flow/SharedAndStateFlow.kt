package com.jyn.masterroad.kotlin.flow

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

/*
 * 协程进阶技巧 - StateFlow和SharedFlow
 * https://juejin.cn/post/6937138168474894343
 *
 * 【译】LiveData的末日？StateFlow简介。
 * https://juejin.cn/post/6844904168910487560
 *
 * （译）从 LiveData 迁移到 Kotlin 的 Flow
 * https://juejin.cn/post/6979116446013980680
 *
 * 打造一个 Kotlin Flow 版的 EventBus
 * https://mp.weixin.qq.com/s/wDdY_P7oPxDxj4Ij-H9oxQ
 */
class SharedAndStateFlow(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Flow"
    }
    /*
     * SharedFlow 和 StateFlow 区别，
     * 1. StateFlow 需要提供初始值，SharedFlow 配置灵活，可提供旧数据同步和缓存配置的功能。
     * 2. SharedFlow配置更为灵活，支持配置replay,缓冲区大小等，StateFlow是SharedFlow的特化版本，replay固定为1，缓冲区大小默认为0
     * 3. StateFlow与LiveData类似，支持通过myFlow.value获取当前状态，如果有这个需求，必须使用StateFlow
     * 4. SharedFlow支持发出和收集重复值，而StateFlow当value重复时，不会回调collect
     *    对于新的订阅者，StateFlow只会重播当前最新值，SharedFlow可配置重播元素个数（默认为0，即不重播）
     *
     *    sharedFlow 无观察者时会清除事件
     */

    //region 一、SharedFlow
    /*
     *  为什么要引入SharedFlow？
     *    冷流和订阅者只能是一对一的关系，当我们要实现一个流，多个订阅者的需求时(这在开发中是很常见的)，就需要热流。
     *    SharedFlow即共享的Flow，可以实现一对多关系,SharedFlow是一种热流
     *
     *  1. replay 表示当新的订阅者Collect时，发送几个已经发送过的数据给它，默认为0，即默认新订阅者不会获取以前的数据
     *  2. extraBufferCapacity 表示减去replay，MutableSharedFlow还缓存多少数据，默认为0
     *  3. onBufferOverflow 表示缓存策略，即缓冲区满了之后Flow如何处理，默认为挂起
     *      3.1 DROP_LATEST 丢掉最新值
     *      3.2 DROP_OLDEST 丢掉最旧值
     *      3.3 SUSPEND 挂起
     */
    private val sharedFlow = MutableSharedFlow<Int>(
        5,             // 参数一：当新的订阅者Collect时，发送几个已经发送过的数据给它
        3, // 参数二：减去replay，MutableSharedFlow还缓存多少数据
        BufferOverflow.SUSPEND // 参数三：缓存策略，
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

    // 扩展方法 shareIn: 将冷流flow转化为SharedFlow
    fun flowShareIn() = mainScope.launch {

        /*
         * 1. scope 表示共享开始时所在的协程作用域范围
         * 2. started 表示控制共享的开始和结束的策略
         *      2.1 SharingStarted.Eagerly          立即开始，而在scope指定的作用域被结束时终止
         *      2.2 SharingStarted.Lazily           在第一个订阅者出现后开始共享数据，在scope指定的作用域被结束时终止
         *      2.3 SharingStarted.WhileSubscribed  见下面详解
         * 3. replay 表示状态流的重播个数
         *
         * WhileSubscribed？
         *   在没有收集器的情况下取消上游数据流,通过shareIn运算符创建的SharedFlow会把数据暴露给视图 (View)，同时也会观察来自其他层级或者是上游应用的数据流。
         *      1. stopTimeoutMillis 以毫秒为单位的延迟值，指的是最后一个订阅者结束订阅与停止上游流的时间差。默认值是 0 (立即停止)。
         *      2. replayExpirationMillis 表示数据重播的过时时间。
         *
         */

        flow {
            (1..100).forEach {
                emit(it)
                delay(100)
            }
        }.shareIn(
            this,
            started = WhileSubscribed(), // 启动政策
            replay = 1
        ).collect {
            LogUtils.tag(TAG).i("shareIn -> collect：$it")
        }
    }
    //endregion


    //region 二、StateFlow
    private val state = MutableStateFlow("初始值")

    /*
     * StateFlow继承于SharedFlow,是SharedFlow的一个特殊变种。
     * StateFlow与LiveData比较相近，相信之所以推出就是为了替换LiveData
     *
     * 1. 它始终是有值的
     * 2. 它的值是唯一的
     * 3. 它允许被多个观察者共用 (因此是共享的数据流)
     * 4. 它永远只会把最新的值重现给订阅者，这与活跃观察者的数量是无关的
     *
     * StateFlow 和 LiveData 有什么区别吗？
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

    fun stateFlowStateIn() = mainScope.launch {
        /**
         * WhileSubscribed: 等待时间过后仍然没有订阅者存在时就自动终止
         * 1. 用户将您的应用转至后台运行，5 秒钟后所有来自其他层的数据更新会停止，这样可以节省电量。
         * 2. 最新的数据仍然会被缓存，所以当用户切换回应用时，视图立即就可以得到数据进行渲染。
         * 3. 订阅将被重启，新数据会填充进来，当数据可用时更新视图。
         * 4. 在屏幕旋转时，因为重新订阅的时间在5s内，因此上游流不会中止
         */
        flow {
            (1..100).forEach {
                emit(it)
                delay(100)
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = 0
        ).collect {
            LogUtils.tag(TAG).i("stateIn -> collect：$it")
        }
    }

    //endregion
}