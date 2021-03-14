package com.jyn.masterroad.thread

import android.view.View
import com.apkfuns.logutils.LogUtils

class ThreadWaitNotifyTest {
    private val lock = Object() //设置一个锁对象

    // region 1. wait 和 notify
    private var count = 100
    private var isWait = false
    private val thread = Thread {
        synchronized(lock) {
            while (count-- > 0) {
                if (isWait) {   //之所以这里，是因为wait只能wait当前线程
                    isWait = false
                    LogUtils.tag("main").i("Thread 被 wait!")
                    lock.wait()
                }
                LogUtils.tag("main").i("Thread 循环次数 :${count}")
                Thread.sleep(1000)
            }
        }
    }

    fun startThread(v:View) {
        thread.start()
    }

    fun waitThread(v: View) {
        isWait = true
    }

    /*
     * notify()         仅仅通知一个线程，并且我们不知道哪个线程会收到通知
     * notifyAll()      会通知所有等待中的线程
     */
    fun notifyThread(v: View) {
        synchronized(lock) {
            lock.notifyAll()
            LogUtils.tag("main").i("Thread 被 notify!")
        }
    }
    // endregion

    //--------------------分割线-----------------------

    // region 2. 生产者消费者模式
    private var items = 0       //商品数量
    private val maxItems = 5    //仓库最大存量

    fun startProduceAndConsume(v: View) {
        for (i in 0..10) {
            Thread { produce() }.apply { name = "produce[生产]:${i}" }.start()
            Thread { consume() }.apply { name = "consume[消费]:${i}" }.start()
        }
    }

    //生产者
    private fun produce() {
        synchronized(lock) {
            if (items >= maxItems) {
                LogUtils.tag("main").i("${Thread.currentThread().name} wait 库存:$items")
                lock.wait() //库存慢了，暂停生产者
            }
            Thread.sleep(100)
            items++ //生产一个
            LogUtils.tag("main").i("${Thread.currentThread().name} produce[生产] --> 库存:$items")
            lock.notifyAll()
        }
    }

    //消费者
    private fun consume() {
        synchronized(lock) {
            if (items <= 0) {
                LogUtils.tag("main").i("${Thread.currentThread().name} wait 库存:$items")
                lock.wait() //没商品了，暂停消费者
            }
            Thread.sleep(100)
            items-- //消费一个
            LogUtils.tag("main").i("${Thread.currentThread().name} consume[消费] --> 库存:$items")
            lock.notifyAll()
        }
    }
    // endregion

    //--------------------分割线-----------------------

    //region 3.死锁
    private val lockA = Object()
    private val lockB = Object()

    private fun deadlockATest() {
        while (true) {
            synchronized(lockA) {
                LogUtils.tag("main").i("${Thread.currentThread()} 已获得锁 lockA 等待获得 lockB")
                Thread.sleep(3000)
                synchronized(lockB) {
                    LogUtils.tag("main").i("${Thread.currentThread()} 已获得锁 lockA 和 lockB")
                    Thread.sleep(30000)
                }
            }
        }
    }

    private fun deadlockBTest() {
        while (true) {
            synchronized(lockB) {
                LogUtils.tag("main").i("${Thread.currentThread()} 已获得锁 lockB 等待获得 lockA")
                Thread.sleep(3000)
                synchronized(lockA) {
                    LogUtils.tag("main").i("${Thread.currentThread()} 已获得锁 lockB 和 lockA")
                    Thread.sleep(30000)
                }
            }
        }
    }

    fun startDeadlock(v: View) {
        Thread { deadlockATest() }.apply { name = "线程A" }.start()
        Thread { deadlockBTest() }.apply { name = "线程B" }.start()
    }
    //endregion

    //--------------------分割线-----------------------

    //region 4.轮流打印ABC

    @Volatile
    var state = 0 // 0对应A，1对应B，2对应C
    fun startPrintABC(v: View) {
        for (i in 1..10) {
            Thread { printA() }.apply { name = "打印A的线程" }.start()
            Thread { printB() }.apply { name = "打印B的线程" }.start()
            Thread { printC() }.apply { name = "打印C的线程" }.start()
        }
    }

    private fun printA() {
        synchronized(lock) {
            while (state != 0) { //开始打印A
                lock.wait() //先保证自己不该打印的时候，处于暂停状态
            }
            LogUtils.tag("main").i("A -->${Thread.currentThread()}")
            state = 1 //打印完改变状态
            lock.notifyAll() //通知对应线程可以继续打印
        }
    }

    private fun printB() {
        synchronized(lock) {
            while (state != 1) { //开始打印B
                lock.wait()
            }
            LogUtils.tag("main").i("B -->${Thread.currentThread()}")
            state = 2
            lock.notifyAll()
        }
    }

    private fun printC() {
        synchronized(lock) {
            while (state != 2) { //开始打印C
                lock.wait()
            }
            LogUtils.tag("main").i("C -->${Thread.currentThread()}")
            state = 0
            lock.notifyAll()
        }
    }

    //endregion
}