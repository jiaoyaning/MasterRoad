package com.jyn.masterroad.concurrent.lock

import com.apkfuns.logutils.LogUtils
import java.util.concurrent.locks.ReentrantReadWriteLock

/*
 * Java并发编程之锁机制之 ReentrantReadWriteLock（读写锁）
 * https://www.jianshu.com/p/416e16eea7da
 *
 * 图文并茂的聊聊ReentrantReadWriteLock的位运算
 * https://mp.weixin.qq.com/s/meupDhgTDbiy2V6CvbZdAg
 */
class ReentrantReadWriteLockTest {
    companion object {
        const val TAG = "ReadWriteLock"
    }

    private var i = 0
    private val reentrantReadWriteLock = ReentrantReadWriteLock()
    private val readLock = reentrantReadWriteLock.readLock()
    private val writeLock = reentrantReadWriteLock.writeLock()

    fun count() {
        writeLock.lock()
        try {
            i++
        } finally {
            writeLock.unlock()
        }
    }

    fun print() {
        readLock.lock()
        try {
            LogUtils.tag(TAG).i("i : $i")
        } finally {
            readLock.unlock()
        }
    }
}