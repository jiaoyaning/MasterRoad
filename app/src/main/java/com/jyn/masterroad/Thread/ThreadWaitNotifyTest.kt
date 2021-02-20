package com.jyn.masterroad.Thread

import com.apkfuns.logutils.LogUtils

class ThreadWaitNotifyTest {
    private val lock = Object() //设置一个锁对象

    private var count = 100
    private var isWait = false

    private val thread: Thread = Thread(Runnable {
        synchronized(lock) {
            while (count-- > 0) {
                if (isWait) { //之所以这里，是因为wait只能wait当前线程
                    isWait = false
                    LogUtils.tag("main").i("Thread 被 wait!")
                    lock.wait()
                }
                LogUtils.tag("main").i("Thread 循环次数 :${count}")
                Thread.sleep(1000)
            }
        }
    })

    fun startThread() {
        thread.start()
    }

    fun waitThread() {
        isWait = true
    }

    fun notifyThread() {
        synchronized(lock) {
            lock.notify()
            LogUtils.tag("main").i("Thread 被 notify!")
        }
    }


//    fun produce() = synchronized(lock) {
//        while (items >= maxItems) {
//            lock.wait()
//        }
//        Thread.sleep(rand.nextInt(100).toLong())
//        items++
//        println("Produced, count is $items: ${Thread.currentThread()}")
//        lock.notifyAll()
//    }
//
//    fun consume() = synchronized(lock) {
//        while (items <= 0) {
//            lock.wait()
//        }
//        Thread.sleep(rand.nextInt(100).toLong())
//        items--
//        println("Consumed, count is $items: ${Thread.currentThread()}")
//        lock.notifyAll()
//    }
}