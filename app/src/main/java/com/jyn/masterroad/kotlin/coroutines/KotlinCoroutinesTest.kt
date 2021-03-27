package com.jyn.masterroad.kotlin.coroutines

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*
import java.util.concurrent.Executors

/*
 * Kotlin协程场景化学习 TODO
 * https://mp.weixin.qq.com/s/zQ7fFKp9CCW6h3TVVE6X5g
 *
 * 协程的参数 TODO
 * https://blog.csdn.net/qq_34589749/article/details/103711621
 */
class KotlinCoroutinesTest(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "Coroutines"
    }

    var lifecycleScope: LifecycleCoroutineScope? = null

    // launch 是非阻塞的
    fun globalScopeTest(v: View) {
        LogUtils.tag(TAG).i("CoroutinesTest 启动 ----->")

        //1. 一个简单的协程🌰
        GlobalScope.launch(
                context = Dispatchers.Unconfined,
                start = CoroutineStart.DEFAULT,
                block = {
                    LogUtils.tag(TAG).i("协程1 in ${Thread.currentThread().name}")
                    delay(1000)
                    LogUtils.tag(TAG).i("协程1 delay后 in ${Thread.currentThread().name}")
                })

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

    fun runBlockingTest(v: View) {
        Thread {
            LogUtils.tag(TAG).i("Thread线程 开始 ————> in ${Thread.currentThread().name}")
            runBlocking {   //runBlocking 是阻塞的
                launch(Dispatchers.Default, CoroutineStart.DEFAULT) {
                    LogUtils.tag(TAG).i("runBlocking1 in ${Thread.currentThread().name}")
                }
                launch(Dispatchers.IO) {
                    LogUtils.tag(TAG).i("runBlocking2 in ${Thread.currentThread().name}")
                }
                launch(Dispatchers.Unconfined) {
                    LogUtils.tag(TAG).i("runBlocking3 in ${Thread.currentThread().name}")
                    delay(1000)
                    LogUtils.tag(TAG).i("runBlocking3 delay后所在的线程 in${Thread.currentThread().name}")
                }
                launch(Executors.newCachedThreadPool().asCoroutineDispatcher()) {
                    LogUtils.tag(TAG).i("runBlocking4 in ${Thread.currentThread().name}")
                    delay(1000)
                    LogUtils.tag(TAG).i("runBlocking4 delay后所在的线程 in${Thread.currentThread().name}")
                }
            }
            LogUtils.tag(TAG).i("Thread线程 结束 ————> in ${Thread.currentThread().name}")
        }.apply { name = "测试子线程" }.start()
    }

    /*
     * 作用域CoroutineScope绑定到LifecycleOwner的生命周期，销毁生命周期的时候，取消此作用域。
     * 作用域的协程也会被取消。并且我们知道LifecycleOwner的生命周期可以和Activity绑定，
     * 因此也就是间接的将CoroutineScope和Activity的生命周期绑定。当Activity被销毁的时候，取消此作用域
     */
    fun lifecycleScopeTest(v: View) {
        /*
         * 1. lifecycleScope.launch() 默认就是在主线程启动协程；
         * 2. lifecycleScope 内的协程在 Lifecycle 为 destroyed 状态时会自动取消。
         * 3. lifecycleScope还有一些其他的扩展方法，如launchWhenCreated、launchWhenStarted、launchWhenResumed等，用法从方法名上看很明显
         */
        lifecycleScope?.launch(Dispatchers.IO) { //默认主线程，Lifecycle为destroyed状态时会自动取消。
            LogUtils.tag(TAG).i("lifecycleScope 默认 in ${Thread.currentThread().name}")
        }
        //至少处于OnCreated状态时，才会执行
        lifecycleScope?.launchWhenCreated {
            LogUtils.tag(TAG).i("lifecycleScope launchWhenCreated in ${Thread.currentThread().name}")
        }
        //至少处于OnResumed状态时，才会执行
        lifecycleScope?.launchWhenResumed {
            LogUtils.tag(TAG).i("lifecycleScope launchWhenResumed in ${Thread.currentThread().name}")
        }
        //至少处于OnStarted状态时，才会执行
        lifecycleScope?.launchWhenStarted {
            LogUtils.tag(TAG).i("lifecycleScope launchWhenStarted in ${Thread.currentThread().name}")
        }
    }

    /*
     * 作用域CoroutineScope绑定到ViewModel，ViewModel被清除的时候，取消此作用域。
     */
    fun viewModelScope(v: View) {
        viewModelScope.launch(Dispatchers.IO) {
            LogUtils.tag(TAG).i("viewModelScope in ${Thread.currentThread().name}")
        }
    }
}