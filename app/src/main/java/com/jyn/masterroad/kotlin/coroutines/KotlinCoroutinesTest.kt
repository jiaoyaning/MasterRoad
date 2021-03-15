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

    fun simpleTest(v: View) {
        LogUtils.tag(TAG).i("CoroutinesTest 启动")

        //1. 一个简单的协程🌰
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            LogUtils.tag(TAG).i("协程1 in ${Thread.currentThread().name}")
        }

        //2. 一个切换线程的协程🌰
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            LogUtils.tag(TAG).i("协程2 in ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {   // 切换到IO线程
                delay(1000)
                LogUtils.tag(TAG).i("协程2 in ${Thread.currentThread().name}")
            }
        }
        LogUtils.tag(TAG).i("CoroutinesTest 主线程 in ${Thread.currentThread().name}")

        //3. 一个执行一半取消的协程🌰
        val job = GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..10000) {
                delay(100)
                LogUtils.tag(TAG).i("协程3 count = $i")
            }
        }
        Thread.sleep(3000)
        job.cancel()
        LogUtils.tag(TAG).i("协程3  主线程 取消")

        //4.生命周期结束自动取消协程的🌰
        lifecycleScope.launch { //默认主线程，Lifecycle为destroyed状态时会自动取消。
            // 切换到IO线程
            withContext(Dispatchers.IO) {
                delay(1000)
                LogUtils.tag(TAG).i("协程4 in ${Thread.currentThread().name}")
            }
            // 自动切回主线程
            LogUtils.tag(TAG).i("协程4 in ${Thread.currentThread().name}")
        }
    }
}