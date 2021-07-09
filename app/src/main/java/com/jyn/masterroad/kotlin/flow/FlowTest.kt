package com.jyn.masterroad.kotlin.flow

import android.app.Application
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 破解 Kotlin 协程(11) - Flow 篇
 * https://mp.weixin.qq.com/s/EkVdbbjbVVCMWjeL6XbfbA
 */

class FlowTest(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Flow"
    }

    /**
     * 冷数据流
     * 不消费则不生产，多次消费则多次生产，生产和消费总是相对应的。
     */

    /**
     * flow { ... } 这种形式，不过在这当中无法随意切换调度器，这是因为 emit 函数不是线程安全的
     */
    @InternalCoroutinesApi
    fun flowTest() = MainScope().launch {
        val flow = flow { //不能在 Flow 中直接切换调度器
            (1..3).forEach {
                emit(it)
                delay(100)
            }
        }.catch {
            LogUtils.tag(TAG).i("flow catch -> $this")
        }.onCompletion {
            LogUtils.tag(TAG).i("flow onCompletion -> $this")
        }

        flow.flowOn(Dispatchers.IO)


        /**
         * collect可消费 Flow 的数据，是最基本的末端操作符，功能与 RxJava 的 subscribe 类似。
         * 末端操作符，大体分为两类：
         *
         */
        flow
            .flowOn(Dispatchers.IO)
            .collect(object : FlowCollector<Int> {
                override suspend fun emit(value: Int) {
                    LogUtils.tag(TAG).i("flow -> collect 1：$value ； ${Thread.currentThread()}")
                }
            })

        flow.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                LogUtils.tag(TAG).i("flow -> collect 2：$value ； ${Thread.currentThread()}")
            }
        })
    }

    /**
     * channelFlow { ... } 想要在生成元素时切换调度器
     */
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    fun channelFlowTest() = MainScope().launch {
        val channelFlow = channelFlow {
            send(1)
            withContext(Dispatchers.IO) {
                send(2)
            }
        }

        channelFlow.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                LogUtils.tag(TAG).i("channelFlow -> collect：$value ； ${Thread.currentThread()}")
            }
        })
    }

    @InternalCoroutinesApi
    fun collectionTest() = MainScope().launch {
        val listOf = listOf(1, 2, 3, 4).asFlow()
        listOf.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                LogUtils.tag(TAG).i("listOf -> collect：$value ； ${Thread.currentThread()}")
            }
        })

        val flowOf = flowOf(1, 2, 3, 4)
        flowOf.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                LogUtils.tag(TAG).i("flowOf -> collect：$value ； ${Thread.currentThread()}")
            }
        })
    }

    /**
     * Flow 的背压
     */
    @InternalCoroutinesApi
    fun backPressure() = MainScope().launch {
        val buffer = flow {
            List(10) {
                emit(it)
            }
        }.buffer()
        buffer.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                LogUtils.tag(TAG).i("buffer -> collect：$value ； ${Thread.currentThread()}")
            }
        })

        delay(1000)

        val conflate = flow { // 新数据会覆盖老数据
            List(10) {
                emit(it)
            }
        }.conflate()
        conflate.collect { value ->
            LogUtils.tag(TAG).i("conflate -> Collecting：$value  ")
            delay(10)
            LogUtils.tag(TAG).i("conflate -> collected：$value ")
        }


        delay(1000)

        /**
         * collectLatest 只处理最新的数据，并不会直接用新数据覆盖老数据，
         * 而是每一个都会被处理，只不过如果前一个还没被处理完后一个就来了的话，处理前一个数据的逻辑就会被取消。
         */
        flow {
            List(10) {
                emit(it)
            }
        }.collectLatest { value ->
            LogUtils.tag(TAG).i("collectLatest -> Collecting：$value  ")
            delay(10)
            LogUtils.tag(TAG).i("collectLatest -> collected：$value ")
        }
    }
}