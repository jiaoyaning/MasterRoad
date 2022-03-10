package com.jyn.masterroad.concurrent.thread

import android.view.View
import com.apkfuns.logutils.LogUtils
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/*
 * CountDownLatch和CyclicBarrier 傻傻的分不清？超长精美图文又来了 TODO
 * https://mp.weixin.qq.com/s/Naui2R2hRxC_teAWt4Rk8g
 *
 * 面试官：说说CountDownLatch，CyclicBarrier，Semaphore的原理？
 * https://mp.weixin.qq.com/s/LDNQMCBXuUfv5ePG0PQUFw
 *
 * CountDownLatch主要作用是使一个线程等待其他线程各自执行完毕后再执行
 */
class CountDownLatchTest {

    /*
     * await可阻塞当前线程，只有等CountDownLatch.countDown()结束后才会继续执行
     */
    fun demo(v: View) {
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
    fun awaitTimeOutTest(v: View) {
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