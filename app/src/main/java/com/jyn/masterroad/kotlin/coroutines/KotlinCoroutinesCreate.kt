package com.jyn.masterroad.kotlin.coroutines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.concurrent.Executors

/*
 * 揭秘kotlin协程中的CoroutineContext
 * https://juejin.cn/post/6926695962354122765
 */
@ExperimentalCoroutinesApi
class KotlinCoroutinesCreate(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "Coroutines"
    }

//region 一. 协程的三种创建方式

    //region 1. runBlocking 阻塞线程
    fun runBlockingTest() {
        Thread {
            LogUtils.tag(TAG).i("线程 开始 ————> in ${Thread.currentThread().name}")
            val runBlocking = runBlocking {
                LogUtils.tag(TAG).i("runBlocking 开始 ————> in ${Thread.currentThread().name}")
                launch(Dispatchers.Main) {
                    LogUtils.tag(TAG).i("Dispatchers.Main delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG).i("Dispatchers.Main delay后 in ${Thread.currentThread().name}")
                }

                delay(100)
                launch(Dispatchers.Default) {
                    LogUtils.tag(TAG)
                        .i("Dispatchers.Default delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG)
                        .i("Dispatchers.Default delay后 in ${Thread.currentThread().name}")
                }

                delay(100)
                launch(Dispatchers.IO) {
                    LogUtils.tag(TAG).i("Dispatchers.IO delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG).i("Dispatchers.IO delay后 in ${Thread.currentThread().name}")
                }

                delay(100)
                launch(Dispatchers.Unconfined) {
                    LogUtils.tag(TAG)
                        .i("Dispatchers.Unconfined delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG)
                        .i("Dispatchers.Unconfined delay后 in${Thread.currentThread().name}")
                }

                delay(100)
                LogUtils.tag(TAG).i("runBlocking 结束 ————> in ${Thread.currentThread().name}")
                "我是runBlocking的返回值，如果不添加该返回值则返回runBlocking的状态"
            }
            LogUtils.tag(TAG)
                .i("线程 结束 ————> in ${Thread.currentThread().name} ;runBlocking: $runBlocking")
        }.start()
    }
    //endregion

    //region 2. GlobalScope 全局生命周期
    fun globalScopeTest() {
        // 1. 完全体协程
        GlobalScope.launch(
            context = Dispatchers.Unconfined,
            start = CoroutineStart.DEFAULT,
            block = {
                LogUtils.tag(TAG).i("1. 完全体参数 in ${Thread.currentThread().name}")
            })

        // 2. Main线程
        sleep(100)
        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("2. Main线程 Dispatchers.Main in ${Thread.currentThread().name}")
        }
        MainScope().launch {
            LogUtils.tag(TAG).i("2. Main线程 MainScope in ${Thread.currentThread().name}")
        }

        // 3. 切换线程测试
        sleep(100)
        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("3. 切换线程 切换前 in ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {   // 切换到IO线程
                LogUtils.tag(TAG).i("3. 切换线程 切换IO线程后 in ${Thread.currentThread().name}")
            }
        }

        // 4. 自定义线程池测试
        sleep(100)
        val newCachedThreadPool = Executors.newCachedThreadPool()
        GlobalScope.launch(newCachedThreadPool.asCoroutineDispatcher()) {
            LogUtils.tag(TAG).i("4. 自定义线程池版 delay前 in ${Thread.currentThread().name}")
            delay(1)
            LogUtils.tag(TAG).i("4. 自定义线程池版 delay后 in ${Thread.currentThread().name}")
            newCachedThreadPool.shutdown() //不用的时候要关闭
        }
    }
    //endregion

    //region 3. CoroutineScope 可控生命周期
    fun coroutineScope() {
        val coroutineScope = CoroutineScope(SupervisorJob())
        coroutineScope.launch {
            LogUtils.tag(TAG).i("CoroutineScope cancel前 in ${Thread.currentThread().name}")
            delay(100)
            LogUtils.tag(TAG).i("CoroutineScope cancel后 in ${Thread.currentThread().name}")
        }
        sleep(50)
        coroutineScope.cancel()
    }
    //endregion

//endregion
// ===================分割线========================
//region 二. 协程的两种启动方式

    //region 1. launch
    fun launch() {
        LogUtils.tag(TAG).i("launch 开始 in ${Thread.currentThread().name}")
        val job = GlobalScope.launch(Dispatchers.Main) {
            for (i in 0..10000) {
                delay(100)
                LogUtils.tag(TAG).i("launch cancel测试 count = $i in ${Thread.currentThread().name}")
            }
        }
        sleep(300)
        job.cancel()
        LogUtils.tag(TAG).i("launch cancel后 in ${Thread.currentThread().name}")
    }
    //endregion

    //region 2. async
    /**
     * async 同 launch 唯一的区别就是 async 是有返回值
     * async 并不会阻塞线程，只是阻塞所调用的协程
     */
    fun async() {
        runBlocking {
            LogUtils.tag(TAG).i("async 开始")
            val deferred1 = GlobalScope.async { doSomethingUsefulOne() }
            val deferred2 = GlobalScope.async { doSomethingUsefulTwo() }
            sleep(300)
            LogUtils.tag(TAG).i("async 结束前状态 one:${deferred1}; two:${deferred2}")
            LogUtils.tag(TAG).i("async 结束 one:${deferred1.await()}; two:${deferred2.await()}")
            LogUtils.tag(TAG).i("async 结束后状态 one:${deferred1}; two:${deferred2}")
        }
    }

    /*
     * suspend关键字只用于提醒这是一个挂起函数，协程运行到该函数的时候会挂起，其本身并没有实际功能。
     * 由suspend修饰的函数只能运行在协程体内，或者运行在suspend修饰的函数内。
     */
    private suspend fun doSomethingUsefulOne(): Int {
        delay(500L)
        LogUtils.tag(TAG).i("async one 运行结束")
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L)
        LogUtils.tag(TAG).i("async two 运行结束")
        return 29
    }

    //endregion

//endregion
// ===================分割线========================
//region 三. 线程切换 & 并行串行

    //region 1.线程切换
    fun withContext() {
        GlobalScope.launch(Dispatchers.Main) { //main是同步执行的
            LogUtils.tag(TAG).i("1 launch 开始 in ${Thread.currentThread().name}")

            //withContext 并不创建新的协程，只是指定协程上运行代码块，且可携带返回值
            val withContext = withContext(Dispatchers.IO) {
                delay(100)
                LogUtils.tag(TAG).i("2 launch 切换IO in ${Thread.currentThread().name}")
                "withContext返回值"
            }
            default()
            LogUtils.tag(TAG)
                .i("4 launch withContext IO后 in ${Thread.currentThread().name} ; withContext:$withContext")
        }
    }

    private suspend fun default() = withContext(Dispatchers.Default) {
        delay(100)
        LogUtils.tag(TAG).i("3 launch 切换Default in ${Thread.currentThread().name}")
    }
    //endregion

    //region 2.并发执行
    fun parallel() {
        /**
         * 协程内部本身是并发的
         */
        GlobalScope.launch {
            for (index in 1..10) {
                launch {
                    delay(1000)
                    LogUtils.tag(TAG).i("并发执行 launch:$index")
                }
            }
        }
    }
    //endregion

    //region 3.同步执行
    fun serial() {
        /**
         * 协程满足以下几点时，可以是同步执行的
         *  1.父协程的协程调度器是处于Dispatchers.Main情况下启动。
         *  2. 同时子协程在不修改协程调度器下的情况下启动。
         */
        GlobalScope.launch(Dispatchers.Main) {
            for (index in 1..10) {
                launch {
                    delay(1000)
                    LogUtils.tag(TAG).i("并发执行 launch:$index")
                }
            }
        }
    }
    //endregion

//endregion
// ===================分割线========================
//region 四. 四种调度器(Dispatchers)


//endregion
// ===================分割线========================
//region 五. 四种启动模式(CoroutineStart)

    //region 1. DEFAULT 默认
    fun startDefault() {
        /*
         * DEFAULT 是饿汉式启动，创建后立即开始调度，但是并不是立即执行，有可能在执行前被取消掉
         */
        LogUtils.tag(TAG).i("startDefault ——> 开始 ")
        val defaultJob = GlobalScope.launch {
            LogUtils.tag(TAG).i("——> DEFAULT ")
        }
        defaultJob.cancel()
        LogUtils.tag(TAG).i("startDefault ——> 结束 ")
    }
    //endregion

    //region 2. LAZY 懒汉式
    fun startLazy() {
        /**
         * 启动后并不会有任何调度行为，直到我们需要它执行的时候才会产生调度
         */
        LogUtils.tag(TAG).i("startLazy ——> 开始 ")
        val lazyJob = GlobalScope.launch(start = CoroutineStart.LAZY) {
            LogUtils.tag(TAG).i("——> LAZY")
        }
        sleep(100)
        lazyJob.start()
        LogUtils.tag(TAG).i("startLazy ——> 结束 ")
    }
    //endregion

    //region 3. ATOMIC
    fun startAtomic() {
        /*
         * 立即执行协程体，但在开始运行之前无法取消
         */
        val atomicJob = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            LogUtils.tag(TAG).i("——> ATOMIC挂起前")
            delay(100)
            LogUtils.tag(TAG).i("——> ATOMIC挂起后")
        }
        atomicJob.cancel()
    }
    //endregion

    //region 4. UNDISPATCHED
    fun startUnDispatched() {
        /*
         * 会直接开始在当前线程下执行，直到运行到第一个挂起点(suspend)。
         */
        val unDispatchedJob = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED){
            LogUtils.tag(TAG).i("——> UNDISPATCHED挂起前")
            delay(100) //delay也是一个suspend方法
            LogUtils.tag(TAG).i("——> UNDISPATCHED挂起后")
        }
        unDispatchedJob.cancel()
    }

    //endregion

//endregion
}