package com.jyn.masterroad.Thread

import com.apkfuns.logutils.LogUtils
import java.util.concurrent.Callable

class ThreadCreate {

    /*
     * 1.继承Thread，重写run()方法
     */
    class ThreadTest : Thread() {
        override fun run() {
            super.run()
            LogUtils.tag("main").i("继承 Thread 后创建的线程")
        }
    }

    /*
     * 2.实现Runnable接口，重写run()方法
     * 可避免Java单继承导致的无法继承Thread实现线程，然而依然需要Thread类才可创建线程
     */
    class RunnableTest : Runnable {
        override fun run() {
            LogUtils.tag("main").i("实现 Runnable 后创建的线程")
        }
    }

    /*
     * 3.使用FutureTask类和Callable接口
     */
    class CallableTest : Callable<String> {
        override fun call(): String {
            LogUtils.tag("main").i("实现 Callable 后创建的线程，可以携带返回值")
            return "这是一个实现了Callable接口的线程"
        }
    }
}