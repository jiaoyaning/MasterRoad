package com.jyn.masterroad.kotlin.flow

import android.app.Application
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/*
 * 破解 Kotlin 协程(11) - Flow 篇
 * https://mp.weixin.qq.com/s/EkVdbbjbVVCMWjeL6XbfbA
 *
 * 抽丝剥茧Kotlin - 协程中绕不过的Flow
 * https://juejin.cn/post/6914802148614242312
 *
 * Kotlin Coroutines Flow 系列(五) 其他的操作符
 * https://www.jianshu.com/p/d672744ad3e0
 */
class FlowCreate(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Flow"
    }

    /*
     * Flow 是冷流
     *    不消费则不生产，多次消费则多次生产，生产和消费总是相对应的。
     *    1. 冷流 :只有订阅者订阅时，才开始执行发射数据流的代码。并且冷流和订阅者只能是一对一的关系，
     *       当有多个不同的订阅者时，消息是重新完整发送的。也就是说对冷流而言，有多个订阅者的时候，他们各自的事件是独立的。
     *    2. 热流:无论有没有订阅者订阅，事件始终都会发生。当热流有多个订阅者时，热流与订阅者们的关系是一对多的关系，可以与多个订阅者共享信息。
     *
     * 有了LiveData和RxJava为什么要引入flow
     *    1. LiveData不支持线程切换，所有数据转换都将在主线程上完成，有时需要频繁更改线程，面对复杂数据流时处理起来比较麻烦
     *    2. RxJava又有些过于麻烦了，有许多让人傻傻分不清的操作符，入门门槛较高，同时需要自己处理生命周期，在生命周期结束时取消订阅
     *
     * flow的优点
     *    1. Flow 支持线程切换、背压
     *    2. Flow 入门的门槛很低，没有那么多傻傻分不清楚的操作符
     *    3. 冷数据流，不消费则不生产数据,这一点与LiveData不同：LiveData的发送端并不依赖于接收端。
     *    4. 属于kotlin协程的一部分，可以很好的与协程基础设施结合
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
         *    1. 集合类型转换操作，包括 toList、toSet 等。
         *    2. 聚合操作，包括将 Flow 规约到单值的 reduce、fold 等操作，以及获得单个元素的操作包括 single、singleOrNull、first 等。
         */
        flow.flowOn(Dispatchers.IO)
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
        val listOf = listOf(1, 2, 3, 4).asFlow().flowOn(Dispatchers.Main)
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