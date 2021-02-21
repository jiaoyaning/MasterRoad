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

    // region 2. 生产者消费者模式
    private var items = 0       //商品数量
    private val maxItems = 5    //仓库最大存量
    private val produceThread by lazy {
        Thread {
            for (i in 0..10) {
                produce()
            }
        }.apply { name = "生产者线程" }
    }
    private val consumeThread by lazy {
        Thread {
            for (i in 0..10) {
                consume()
            }
        }.apply { name = "消费者线程" }
    }

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

    fun startProduceAndConsume() {
        produceThread.start()
        consumeThread.start()
    }
    // endregion
}