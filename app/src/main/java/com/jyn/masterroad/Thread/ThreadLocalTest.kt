package com.jyn.masterroad.Thread

import com.apkfuns.logutils.LogUtils

/*
 * ThreadLocal详解
 * https://mp.weixin.qq.com/s/lHntG7C_nBa_a59gMdfx1Q
 */
class ThreadLocalTest {
    private val threadLocal: ThreadLocal<String> by lazy {
        ThreadLocal<String>().apply {
            set("threadLocal 父线程 value")
        }
    }

    /*
     * 经过测试，可以得出一个结论，无论ThreadLocal被其他线程如何改变，对于本线程来说都是唯一的。
     * 子线程同样获取不到父线程的ThreadLocal
     * 当 ThreadLocal 调用 set或get 方法时，ThreadLocalMap 才会被真正创建，并用于存储数据
     *
     * 内存泄漏问题:
     * ThreadLocalMap中的Entry的key使用的是ThreadLocal对象的弱引用，在没有其他地方对ThreadLocal依赖，ThreadLocalMap中的ThreadLocal对象就会被回收掉，
     * 但是对应的不会被回收，这个时候Map中就可能存在key为null但是value不为null的项，这需要实际的时候使用完毕及时调用remove方法避免内存泄漏。
     */
    fun threadLocalTest() {
        Thread {
            LogUtils.tag("main").i("threadLocal A线程 改前 ${threadLocal.get()}")
            threadLocal.set("threadLocal A线程 改成 A")
            Thread.sleep(1000)
            LogUtils.tag("main").i("threadLocal A线程 改后 ${threadLocal.get()}")
            threadLocal.remove()
            LogUtils.tag("main").i("threadLocal A线程 remove()后 ${threadLocal.get()}")
        }.start()

        Thread {
            LogUtils.tag("main").i("threadLocal B线程 改前 ${threadLocal.get()}")
            threadLocal.set("threadLocal B线程 改成 B")
            Thread.sleep(1000)
            LogUtils.tag("main").i("threadLocal B线程 改后 ${threadLocal.get()}")
            threadLocal.remove()
            LogUtils.tag("main").i("threadLocal B线程 remove()后 ${threadLocal.get()}")

        }.start()

        LogUtils.tag("main").i("threadLocalMain 改后 ${threadLocal.get()}")
    }


    /**
     * 可以把父线程变量传递到子线程的ThreadLocal，不可逆向传递(子传父)
     * 而父线程创建子线程的时候，ThreadLocalMap中的构造函数会将父线程的inheritableThreadLocals中的变量复制一份到子线程的inheritableThreadLocals变量中。
     */
    private val inheritableThreadLocal: InheritableThreadLocal<String> by lazy {
        InheritableThreadLocal<String>().apply {
            set("inheritableThreadLocal 父线程")
        }
    }

    fun inheritableThreadLocalTest() {
        LogUtils.tag("main").i("主线程 ${inheritableThreadLocal.get()}")
        Thread {
            LogUtils.tag("main").i("子线程 获取父线程 ${inheritableThreadLocal.get()}")
            inheritableThreadLocal.set("inheritableThreadLocal 子线程")
            LogUtils.tag("main").i("子线程 改后 ${inheritableThreadLocal.get()}")
        }.start()
        Thread.sleep(1000)
        LogUtils.tag("main").i("主线程 ${inheritableThreadLocal.get()}")
    }
}