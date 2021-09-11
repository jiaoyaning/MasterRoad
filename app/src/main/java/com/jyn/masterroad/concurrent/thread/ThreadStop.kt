package com.jyn.masterroad.concurrent.thread

import com.apkfuns.logutils.LogUtils

/*
 * 腾讯面试官：如何停止一个正在运行的线程？我蒙了
 * https://mp.weixin.qq.com/s/EZJNIP45D1alLq1VNx8q_g
 */
class ThreadStop {
    companion object {
        const val TAG = "stop"
    }

    //region 1.stop 过时
    fun stop() {
        val thread = Thread {
            for (i in 0..1000000) {
                LogUtils.tag(TAG).i("number: $i")
            }
        }
        thread.start()
        Thread.sleep(1000)
        thread.stop()
    }
    //endregion

    //region 2.interrupt 中断标记
    fun interrupt() {
        val thread = object : Thread() {
            override fun run() {
                super.run()
                for (i in 0..1000000) {
                    //在代码最前面return，可以避免后序代码执行，浪费性能
                    if (isInterrupted) return
                    LogUtils.tag(TAG).i("number: $i")
                }
            }
        }
        thread.start()
        /*
         * sleep()方法 java需要try catch。
         * 原因：线程在sleep过程中，外界调用interrupt()方法，会先使sleep()方法抛出异常，来直接标记线程中断睡眠
         */
        Thread.sleep(1000)
        thread.interrupt()
    }
    //endregion

    //region 3.异常法
    fun exception() {
        
    }
    //endregion
}