package com.jyn.masterroad.concurrent

import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList

/**
 * 美团面试：JVM 堆内存溢出后，其他线程是否可继续工作？
 * https://mp.weixin.qq.com/s/5W1Tb0uC9S9UAtAvY2UsrA
 */
class ThreadTryDemo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ThreadTryDemo().threadOOMTest()
        }
    }

    fun threadOOMTest() {
        Thread {
            val list: MutableList<ByteArray> = ArrayList()
            while (true) {
                println(Date().toString() + Thread.currentThread() + "==")
                list.add(ByteArray(1024 * 1024 * 1024))
                sleep(100)
            }
        }.start()

        Thread {
            while (true) {
                println(Date().toString() + Thread.currentThread() + "==")
                sleep(100)
            }
        }.start()
    }
}