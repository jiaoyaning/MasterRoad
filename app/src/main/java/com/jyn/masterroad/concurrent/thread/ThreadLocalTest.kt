package com.jyn.masterroad.concurrent.thread

import android.view.View
import com.apkfuns.logutils.LogUtils
import javax.inject.Inject

/*
 * ThreadLocal详解
 * https://mp.weixin.qq.com/s/lHntG7C_nBa_a59gMdfx1Q
 *
 * 保姆级教学，22张图揭开ThreadLocal
 * https://mp.weixin.qq.com/s/qMep17llDFWHq_S0IWh6ZA
 *
 * 一个ThreadLocal和面试官大战30个回合
 * https://mp.weixin.qq.com/s/JUb2GR4CmokO0SklFeNmwg
 *
 * 自信，这是最好的ThreadLocal分析
 * https://mp.weixin.qq.com/s/8qfx7sD_vS0iT6wnZCIMtQ
 *
 * ThreadLocal的奇思妙想（优秀）
 * https://mp.weixin.qq.com/s/ucxzyP3GlOdjCRlQgOiy9g
 *
 * 弱引用什么时候被回收_面试官：ThreadLocal为什么会发生内存泄漏？
 * https://blog.csdn.net/weixin_39948210/article/details/110902604
 *
 * 细数ThreadLocal三大坑，内存泄露仅是小儿科
 * https://mp.weixin.qq.com/s/eWgTmP283kD_M2VxSxvYag
 *
 * ThreadLocal的短板，我TTL来补！
 * https://mp.weixin.qq.com/s/JTW82eTZRyKbkgOe2E_qEg
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
     * ThreadLocalMap中的Entry的key使用的是ThreadLocal对象的弱引用，
     * 在没有其他地方对ThreadLocal依赖时，ThreadLocalMap中的ThreadLocal对象就会被回收掉，
     * 但是对应的value不会被回收，这个时候Map中就可能存在key为null但是value不为null的项，
     * 这需要实际的时候使用完毕及时调用remove方法避免内存泄漏。
     */
    fun threadLocalTest(v: View) {
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

    /*
     * Thread对象维护了一个key为ThreadLocal的Map
     * 该方法可证明，每个Thread的是ThreadLocal对象唯一，而不是只能声明一个ThreadLocal对象
     */
    fun threadLocalInnerTest(v: View) {
        Thread {
            val threadLocal1: ThreadLocal<String> = ThreadLocal()
            val threadLocal2: ThreadLocal<String> = ThreadLocal()
            threadLocal1.set("我是ThreadLocal1的value")
            threadLocal2.set("我是ThreadLocal2的value")
            LogUtils.tag("main").i("threadLocal1:${threadLocal1.get()} ; threadLocal2:${threadLocal2.get()}")
        }.start()
    }

    /*
     * 可以把父线程变量传递到子线程的ThreadLocal，不可逆向传递(子传父)
     * 父线程创建子线程的时候，ThreadLocalMap中的构造函数会将父线程的inheritableThreadLocals中的变量复制一份到子线程的inheritableThreadLocals变量中。
     */
    private val inheritableThreadLocal: InheritableThreadLocal<String> by lazy {
        InheritableThreadLocal<String>().apply {
            set("inheritableThreadLocal 父线程")
        }
    }

    fun inheritableThreadLocalTest(v: View) {
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