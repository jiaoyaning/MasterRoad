package com.jyn.masterroad.kotlin.coroutines

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.util.concurrent.Executors

/*
 * Kotlin协程场景化学习 TODO
 * https://mp.weixin.qq.com/s/zQ7fFKp9CCW6h3TVVE6X5g
 *
 * 官方文档
 * https://www.kotlincn.net/docs/reference/coroutines/basics.html
 */
@ExperimentalCoroutinesApi
class KotlinCoroutinesTest(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "Coroutines"
    }

    var lifecycleScope: LifecycleCoroutineScope? = null

    // launch 是非阻塞的
    fun globalScopeTest(v: View) {
        LogUtils.tag(TAG).i("主线程 -----> in ${Thread.currentThread().name}")

        //完全体协程
        GlobalScope.launch(
            context = Dispatchers.Unconfined,
            start = CoroutineStart.DEFAULT,
            block = {
                LogUtils.tag(TAG).i("1 完全参数版 in ${Thread.currentThread().name}")
            })

        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("2 Dispatchers.Main in ${Thread.currentThread().name}")
        }
        // 等价于 同上的Dispatchers.Main
        MainScope().launch {
            LogUtils.tag(TAG).i("2 MainScope in ${Thread.currentThread().name}")
        }

        //切换线程测试
        GlobalScope.launch(Dispatchers.Main) {
            LogUtils.tag(TAG).i("3 切换线程前 in ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {   // 切换到IO线程
                LogUtils.tag(TAG).i("3 切换到IO线程后 in ${Thread.currentThread().name}")
            }
        }

        //自定义线程池测试
        val newCachedThreadPool = Executors.newCachedThreadPool()
        GlobalScope.launch(newCachedThreadPool.asCoroutineDispatcher()) {
            LogUtils.tag(TAG).i("自定义线程池版 delay前 in ${Thread.currentThread().name}")
            delay(1)
            LogUtils.tag(TAG).i("自定义线程池版 delay后 in ${Thread.currentThread().name}")
            newCachedThreadPool.shutdown() //不用的时候要关闭
        }

        //cancel测试测试
        val job = GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..10000) {
                delay(100)
                LogUtils.tag(TAG).i("协程cancel测试 count = $i in ${Thread.currentThread().name}")
            }
        }
        Thread.sleep(300)
        job.cancel()
        LogUtils.tag(TAG).i("协程3 cancel后 in ${Thread.currentThread().name}")
    }

    /**
     * suspend是提醒这是一个挂起函数，协程运行到该函数的时候，会挂起。
     * 当然suspend不是实现挂起的作用。只是用来提醒，真正实现挂起的是suspend修饰的函数中的代码。
     * suspend函数因为是用来提醒挂起协程的，所以suspend修饰的函数只能运行在协程体内，或者运行在suspend修饰的函数内。
     */
    fun asyncAndProduceTest(v: View) {
        runBlocking {
            /**
             * async 和 launch 函数的区别就是， async 执行的代码块有返回值，
             * 通过 Deferred 对象可以获取到这个代码块异步执行后的结果。
             */
            val async = GlobalScope.async(Dispatchers.IO) {
                delay(30)
                LogUtils.tag(TAG).i("async 添加返回值")
                return@async "[async测试返回值]"
            }
            async.await() //await会阻塞主线程
            LogUtils.tag(TAG).i("async 返回值 : ${async.getCompleted()}")

            val produce = GlobalScope.produce {
                for (i in 0..10) {
                    delay(10)
                    send("produce 发送 $i")
                }
            }
            produce.consumeEach {
                LogUtils.tag(TAG).i("produce 接受 $it")
            }
        }
    }

    //阻塞线程的协程
    fun runBlockingTest(v: View) {
        LogUtils.tag(TAG).i("主线程 ————> in ${Thread.currentThread().name}")
        Thread {
            LogUtils.tag(TAG).i("子线程开始 ————> in ${Thread.currentThread().name}")
            runBlocking {   //runBlocking 是阻塞的
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
            }
            LogUtils.tag(TAG).i("子线程 结束 ————> in ${Thread.currentThread().name}")
        }.apply { name = "子线程" }.start()
    }

    fun coroutineStartTest(v: View) {
        // 根据其上下文立即安排协程执行，调用cancel()函数后，无法执行
        val default = GlobalScope.launch(start = CoroutineStart.DEFAULT) {
            LogUtils.tag(TAG).i("DEFAULT delay in ${Thread.currentThread().name}")
            delay(50)
            LogUtils.tag(TAG).i("DEFAULT cancel是否完成？ in ${Thread.currentThread().name}")
        }
        Thread.sleep(30)
        LogUtils.tag(TAG).i("DEFAULT cancel()后，DEFAULT协程会被中断")
        default.cancel()

        //仅在需要时才延迟启动，创建之后，不会立即执行，只在调用了它的start()或者join()方法的时候，才会执行。
        val lazy = GlobalScope.launch(start = CoroutineStart.LAZY) {
            LogUtils.tag(TAG).i("LAZY delay前 in ${Thread.currentThread().name}")
            delay(50)
            LogUtils.tag(TAG).i("LAZY delay后 in ${Thread.currentThread().name}")
        }
        Thread.sleep(20)
        lazy.start()
        LogUtils.tag(TAG).i("LAZY 只有在调用start()或者join()后，LAZY协程才会执行")
        Thread.sleep(60)


        //原子地（以不可取消的方式）根据协程的上下文来调度协程以使其执行；这类似于[DEFAULT]，但协程不能在开始执行之前被取消。
        val atomic = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            LogUtils.tag(TAG).i("ATOMIC delay前 in ${Thread.currentThread().name}")
            delay(50)
            LogUtils.tag(TAG).i("ATOMIC delay后 in ${Thread.currentThread().name}")
        }
        LogUtils.tag(TAG).i("ATOMIC cancel()也不能取消ATOMIC协程")
        atomic.cancel()
        Thread.sleep(60)
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
            LogUtils.tag(TAG)
                .i("lifecycleScope launchWhenCreated in ${Thread.currentThread().name}")
        }
        //至少处于OnResumed状态时，才会执行
        lifecycleScope?.launchWhenResumed {
            LogUtils.tag(TAG)
                .i("lifecycleScope launchWhenResumed in ${Thread.currentThread().name}")
        }
        //至少处于OnStarted状态时，才会执行
        lifecycleScope?.launchWhenStarted {
            LogUtils.tag(TAG)
                .i("lifecycleScope launchWhenStarted in ${Thread.currentThread().name}")
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