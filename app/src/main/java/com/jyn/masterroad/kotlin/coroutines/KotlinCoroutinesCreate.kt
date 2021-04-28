package com.jyn.masterroad.kotlin.coroutines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.concurrent.Executors

/**
 * Created by jiaoyaning on 2021/4/27.
 */
class KotlinCoroutinesCreate(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "Coroutines"
    }

    //region 协程的三种创建方式 runBlocking & GlobalScope & CoroutineScope

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

    //region 协程的两种启动方式 launch & async

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
    suspend fun async() {
        val one = GlobalScope.async { doSomethingUsefulOne() }
        val two = GlobalScope.async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // 假设我们在这里做了些有用的事
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这里也做了些有用的事
        return 29
    }

    //endregion

    //endregion
}