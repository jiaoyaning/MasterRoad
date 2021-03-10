package com.jyn.masterroad.Thread

import com.apkfuns.logutils.LogUtils
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/*
 * CountDownLatch主要作用是使一个线程等待其他线程各自执行完毕后再执行
 *
 */
class CountDownLatchTest {

    /*
     * await可阻塞当前线程，只有等CountDownLatch.countDown()结束后才会继续执行
     */
    fun demo() {
        val latch = CountDownLatch(2)
        Thread {
            LogUtils.tag("main").i("ThreadA 启动")
            Thread.sleep(1000)
            latch.countDown()
            LogUtils.tag("main").i("ThreadA 执行完毕")
        }.start()

        Thread {
            LogUtils.tag("main").i("ThreadB 启动")
            Thread.sleep(2000)
            latch.countDown()
            LogUtils.tag("main").i("ThreadB 执行完毕")
        }.start()

        LogUtils.tag("main").i("latch 等待中...")
        latch.await()
        LogUtils.tag("main").i("latch 等待结束...")
    }

    /*
     * timeout结束后会中断阻塞，继续执行
     */
    fun awaitTimeOutTest() {
        val latch = CountDownLatch(2)
        Thread {
            LogUtils.tag("main").i("ThreadA 启动")
            Thread.sleep(1000)
//            latch.countDown()
            LogUtils.tag("main").i("ThreadA 执行完毕")
        }.start()

        Thread {
            LogUtils.tag("main").i("ThreadB 启动")
            Thread.sleep(2000)
            latch.countDown()
            LogUtils.tag("main").i("ThreadB 执行完毕")
        }.start()

        LogUtils.tag("main").i("latch 等待中...")
        latch.await(1, TimeUnit.SECONDS)
        LogUtils.tag("main").i("latch 等待结束...")
    }
}