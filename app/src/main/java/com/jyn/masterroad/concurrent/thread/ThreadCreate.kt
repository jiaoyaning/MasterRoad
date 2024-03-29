package com.jyn.masterroad.concurrent.thread

import com.apkfuns.logutils.LogUtils
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import java.util.concurrent.ThreadFactory
import javax.inject.Inject

/*
 * 廖雪峰的官方网站 - 使用CompletableFuture
 * https://www.liaoxuefeng.com/wiki/1252599548343744/1306581182447650
 */
class ThreadCreate {

    //region 一、线程创建的几种方式

    //region 1.继承Thread类
    fun threadTest() {
        val threadTest = ThreadTest()
        threadTest.start()
    }

    class ThreadTest : Thread() {
        override fun run() {
            super.run()
            LogUtils.tag("main").i("继承 Thread 后创建的线程")
        }
    }
    //endregion

    //region 2.实现Runnable接口，可避免Java单继承问题，仍需要Thread类
    fun runnableThreadTest() {
        val runnableTest = RunnableTest() // 多线程情况下可共享变量，但并不能保证线程安全，只加volatile不行，同时需要Synchronized
        val runnableThreadTest1 = Thread(runnableTest)
        val runnableThreadTest2 = Thread(runnableTest)
        runnableThreadTest1.start()
        runnableThreadTest2.start()
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
    //endregion

    //region 3.FutureTask类 + Callable接口，可携带返回值，需要Thread类或者Executors
    fun futureTaskTest() {
        val callableTest: Callable<String> = CallableTest()
        val futureTaskTest: FutureTask<String> = FutureTask(callableTest)

        /**
         * Future.get()会阻塞当前线程
         * 可以用Future.isDone()来循环判断子线程是否执行结束，再进行get操作
         */
        //第一种futureTask方式 使用线程池
        val executor = Executors.newCachedThreadPool()
        val submit = executor.submit(callableTest)
        LogUtils.tag("main").i("第一种Future实现方式: Executors.submit(Callable) 返回结果:${submit.get()}")
        executor.shutdown()

        //第二种futureTask方式 使用Thread
        Thread(futureTaskTest).start()
        LogUtils.tag("main").i("第二种Future实现方式: Thread(FutureTask) 返回结果:${futureTaskTest.get()}")
    }

    class CallableTest : Callable<String> {
        override fun call(): String {
            LogUtils.tag("main").i("实现 Callable 后创建的线程，可以携带返回值")
            return "这是一个实现了Callable接口的线程"
        }
    }
    //endregion

    //region 4.CompletableFuture 可串行可并行的线程
    fun completableFutureTest() {
        /**
         * 优点：设置好回调后，就不用再关系异步阻塞主流程问题
         * 缺点：最终结果还是在异步之中，脱离了主线程调用
         */

    }
    //endregion

    //region 5.借助Executors(线程池)
    fun executorsTest() {
        val es = Executors.newCachedThreadPool()
        es.execute { LogUtils.tag("main").i("使用 Executors 创建的线程") }
    }
    //endregion

    // region 6.ThreadFactory
    fun threadFactory() {
        val threadFactory: ThreadFactory = ThreadFactory { Thread() }
        threadFactory.newThread {

        }
    }

    //endregion

    //endregion

    //region 二、提高线程优先级的两种方式

    //region 1.JDK提供的方法
    fun setPrioriy() {
        Thread {
            Thread.currentThread().priority = 1
        }.start()
    }
    //endregion

    //region 2.OS提供的方法
    fun setThreadPriority() {
        Thread {
            android.os.Process.setThreadPriority(0)
        }.start()
    }
    //endregion

    //endregion

    //region 三、停止线程的几种方式

    //endregion
}