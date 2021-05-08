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
class KotlinCoroutinesCreate(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "Coroutines"
    }

    //region 一. 协程的三种创建方式

    //region 1. runBlocking 阻塞线程
    fun runBlockingTest() {
        Thread {
            LogUtils.tag(TAG).i("线程 开始 ————> in ${Thread.currentThread().name}")
            runBlocking {
                LogUtils.tag(TAG).i("runBlocking 开始 ————> in ${Thread.currentThread().name}")
                launch(Dispatchers.Main) {
                    LogUtils.tag(TAG).i("Dispatchers.Main delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG).i("Dispatchers.Main delay后 in ${Thread.currentThread().name}")
                }

                delay(100)
                launch(Dispatchers.Default) {
                    LogUtils.tag(TAG).i("Dispatchers.Default delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG).i("Dispatchers.Default delay后 in ${Thread.currentThread().name}")
                }

                delay(100)
                launch(Dispatchers.IO) {
                    LogUtils.tag(TAG).i("Dispatchers.IO delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG).i("Dispatchers.IO delay后 in ${Thread.currentThread().name}")
                }

                delay(100)
                launch(Dispatchers.Unconfined) {
                    LogUtils.tag(TAG).i("Dispatchers.Unconfined delay前 in ${Thread.currentThread().name}")
                    delay(50)
                    LogUtils.tag(TAG).i("Dispatchers.Unconfined delay后 in${Thread.currentThread().name}")
                }

                delay(100)
                LogUtils.tag(TAG).i("runBlocking 结束 ————> in ${Thread.currentThread().name}")
            }
            LogUtils.tag(TAG).i("线程 结束 ————> in ${Thread.currentThread().name}")
        }.start()
    }
    //endregion

    //region 2. GlobalScope 全局生命周期
    fun globalScopeTest() {
        // 完全体协程
        GlobalScope.launch(
                context = Dispatchers.Unconfined,
                start = CoroutineStart.DEFAULT,
                block = { LogUtils.tag(TAG).i("完全体参数 in ${Thread.currentThread().name}") })

        // Main线程
        sleep(100)
        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("Dispatchers.Main in ${Thread.currentThread().name}")
        }
        MainScope().launch {
            LogUtils.tag(TAG).i("MainScope 测试 in ${Thread.currentThread().name}")
        }

        // 切换线程测试
        sleep(100)
        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("切换线程 切换前 in ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {   // 切换到IO线程
                LogUtils.tag(TAG).i("切换线程 切换IO线程后 in ${Thread.currentThread().name}")
            }
        }

        // 自定义线程池测试
        sleep(100)
        val newCachedThreadPool = Executors.newCachedThreadPool()
        GlobalScope.launch(newCachedThreadPool.asCoroutineDispatcher()) {
            LogUtils.tag(TAG).i("自定义线程池版 delay前 in ${Thread.currentThread().name}")
            delay(1)
            LogUtils.tag(TAG).i("自定义线程池版 delay后 in ${Thread.currentThread().name}")
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
            val oneDeferred = GlobalScope.async { doSomethingUsefulOne() }
            val twoDeferred = GlobalScope.async { doSomethingUsefulTwo() }
            sleep(300)
            LogUtils.tag(TAG).i("async 结束 one:${oneDeferred.await()}; two:${twoDeferred.await()}")
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

    //region 三. 线程切换

    fun withContext() {
        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("launch 开始 in ${Thread.currentThread().name}")

            //withContext 并不创建新的协程，只是指定协程上运行代码块
            withContext(Dispatchers.IO) {
                delay(1000)
                LogUtils.tag(TAG).i("launch 切换IO in ${Thread.currentThread().name}")
            }

            default()

            LogUtils.tag(TAG).i("launch withContext IO后 in ${Thread.currentThread().name}")
        }
    }

    private suspend fun default() = withContext(Dispatchers.Default) {
        delay(1000)
        LogUtils.tag(TAG).i("launch 切换Default in ${Thread.currentThread().name}")
    }

    //endregion
}