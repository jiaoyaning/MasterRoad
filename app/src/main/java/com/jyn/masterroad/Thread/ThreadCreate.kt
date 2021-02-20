package com.jyn.masterroad.Thread

import com.apkfuns.logutils.LogUtils
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

/**
 * 线程创建的四种方式
 */
class ThreadCreate {

    class ThreadTest : Thread() {
        override fun run() {
            super.run()
            LogUtils.tag("main").i("继承 Thread 后创建的线程")
        }
    }

    class RunnableTest : Runnable {
        @Volatile
        var countDown = 4

        @Synchronized
        override fun run() {
            while (countDown-- > 0) {
                LogUtils.tag("main").i("实现 Runnable 后创建的线程 countDown:$countDown")
            }
        }
    }

    class CallableTest : Callable<String> {
        override fun call(): String {
            LogUtils.tag("main").i("实现 Callable 后创建的线程，可以携带返回值")
            return "这是一个实现了Callable接口的线程"
        }
    }

    // region 一、线程创建的四种方式
    // 1.继承Thread类
    fun threadTest() {
        val threadTest = ThreadTest()
        threadTest.start()
    }

    // 2.实现Runnable接口，可避免Java单继承问题，仍需要Thread类
    fun runnableThreadTest() {
        val runnableTest = RunnableTest()
        // 多线程情况下可共享变量，但并不能保证线程安全，只加volatile不行，同时需要Synchronized
        val runnableThreadTest1 = Thread(runnableTest)
        val runnableThreadTest2 = Thread(runnableTest)
        runnableThreadTest1.start()
        runnableThreadTest2.start()
    }

    // 3.FutureTask类 + Callable接口，可携带返回值，需要Thread类或者Executors
    fun futureTaskTest() {
        val callableTest: Callable<String> = ThreadCreate.CallableTest()
        val futureTaskTest: FutureTask<String> = FutureTask<String>(callableTest)

        //第一种futureTask方式 使用线程池
        val executor = Executors.newCachedThreadPool()
        val submit = executor.submit(callableTest)
        LogUtils.tag("main").i("第一种Future实现方式: Executors.submit(Callable) 返回结果:${submit.get()}")
        executor.shutdown()

        //第二种futureTask方式 使用Thread
        Thread(futureTaskTest).start()
        LogUtils.tag("main").i("第二种Future实现方式: Thread(FutureTask) 返回结果:${futureTaskTest.get()}")
    }

    // 4.借助Executors(线程池)
    fun executorsTest() {
        val es = Executors.newCachedThreadPool()
        es.execute { LogUtils.tag("main").i("使用 Executors 创建的线程") }
    }
    // endregion
}