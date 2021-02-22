package com.jyn.masterroad.Thread

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

    fun startThread() {
        thread.start()
    }

    fun waitThread() {
        isWait = true
    }

    /*
     * notify()         仅仅通知一个线程，并且我们不知道哪个线程会收到通知
     * notifyAll()      会通知所有等待中的线程
     */
    fun notifyThread() {
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

    //生产者
    private fun produce() {
        synchronized(lock) {
            if (items >= maxItems) {
                LogUtils.tag("main").i("${Thread.currentThread()} wait 库存:$items")
                lock.wait() //库存慢了，暂停生产者
            }
            Thread.sleep(800)
            items++ //生产一个
            LogUtils.tag("main").i("produce 生产一个商品 库存:$items")
            lock.notifyAll()
        }
    }

    //消费者
    private fun consume() {
        synchronized(lock) {
            if (items <= 0) {
                LogUtils.tag("main").i("${Thread.currentThread()} wait 库存:$items")
                lock.wait() //没商品了，暂停消费者
            }
            Thread.sleep(1000)
            items-- //消费一个
            LogUtils.tag("main").i("consume 消费一个商品 库存:$items")
            lock.notifyAll()
        }
    }

    /**
     * 有两种方式
     * 1、生产者消费者只有一个独立线程
     * 2、每个生产者和消费者都是一个独立线程
     */
    fun startProduceAndConsume() {
        Thread {
            for (i in 0..10) {
                produce()
            }
        }.apply { name = "生产者线程" }.start()

        Thread {
            for (i in 0..10) {
                consume()
            }
        }.apply { name = "消费者线程" }.start()
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

    fun startDeadlock() {
        Thread { deadlockATest() }.apply { name = "线程A" }.start()
        Thread { deadlockBTest() }.apply { name = "线程B" }.start()
    }
    //endregion
}