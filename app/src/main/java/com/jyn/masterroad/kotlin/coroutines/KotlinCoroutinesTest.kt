package com.jyn.masterroad.kotlin.coroutines

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*

/*
 * Kotlin协程场景化学习 TODO
 * https://mp.weixin.qq.com/s/zQ7fFKp9CCW6h3TVVE6X5g
 */
class KotlinCoroutinesTest(private val lifecycleScope: LifecycleCoroutineScope) {
    private val TAG = "Coroutines"

    // launch 是非阻塞的
    fun globalScopeTest(v: View) {
        LogUtils.tag(TAG).i("CoroutinesTest 启动 ----->")

        //1. 一个简单的协程🌰
        GlobalScope.launch(Dispatchers.Unconfined) {
            LogUtils.tag(TAG).i("协程1 in ${Thread.currentThread().name}")
            delay(1000)
            LogUtils.tag(TAG).i("协程1 delay后 in ${Thread.currentThread().name}")
        }

        //2. 一个切换线程的协程🌰
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            LogUtils.tag(TAG).i("协程2 in ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {   // 切换到IO线程
                delay(1000)
                LogUtils.tag(TAG).i("协程2 切换到IO线程后 in ${Thread.currentThread().name}")
            }
        }
        LogUtils.tag(TAG).i("CoroutinesTest 主线程 -----> in ${Thread.currentThread().name}")

        //3. 一个执行一半取消的协程🌰
        val job = GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..10000) {
                delay(1000)
                LogUtils.tag(TAG).i("协程3 IO线程 循环 count = $i in ${Thread.currentThread().name}")
            }
        }
        Thread.sleep(3000)
        job.cancel()
        LogUtils.tag(TAG).i("协程3 cancel后 in ${Thread.currentThread().name}")
    }

    fun globalScopeThreadTest(v: View) {
        Thread {
            //runBlocking 是阻塞的
            runBlocking {

            }
        }.start()
    }

    //4.生命周期结束自动取消协程的🌰
    fun lifecycleCoroutineScopeTest(v: View) {
        /*
         * 1. lifecycleScope.launch() 默认就是在主线程启动协程；
         * 2. lifecycleScope 内的协程在 Lifecycle 为 destroyed 状态时会自动取消。
         * 3. lifecycleScope还有一些其他的扩展方法，如launchWhenCreated、launchWhenStarted、launchWhenResumed等，用法从方法名上看很明显
         */

        lifecycleScope.launch { //默认主线程，Lifecycle为destroyed状态时会自动取消。
            // 切换到IO线程
            withContext(Dispatchers.IO) {
                delay(1000)
                LogUtils.tag(TAG).i("LifecycleCoroutineScope in ${Thread.currentThread().name}")
            }
            // 自动切回主线程
            LogUtils.tag(TAG).i("LifecycleCoroutineScope in ${Thread.currentThread().name}")
        }
    }
}