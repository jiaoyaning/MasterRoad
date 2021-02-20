package com.jyn.masterroad.Thread

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityThreadBinding
import java.util.concurrent.*

/*
 * JDK线程池源码分析之ThreadPoolExecutor
 * https://www.jianshu.com/p/072703367564
 *
 * ExecutorService 的 shutdown 和 shutdownNow 区别与联系
 * https://www.jianshu.com/p/f2591bdd0877
 *
 * 线程攻略，夯实基础很重要！
 * https://juejin.cn/post/6866834999081959438
 *
 * 2w字长文带你深入理解线程池
 * https://mp.weixin.qq.com/s/c4A4ow-TDaG6COArX1rXmg
 *
 * synchronized 的使用场景和原理简介
 * https://www.cnblogs.com/54chensongxia/p/11899031.html
 *
 * Thread.yield()和Thread.sleep(0)
 * https://blog.csdn.net/qq_15037231/article/details/103440060
 * https://blog.csdn.net/u013218720/article/details/21947613
 */
@Route(path = RoutePath.Thread.path)
class ThreadActivity : BaseActivity<ActivityThreadBinding>() {

    override fun getLayoutId() = R.layout.activity_thread

    private val executor: ExecutorService by lazy {
//        Executors.newCachedThreadPool()           //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//        Executors.newSingleThreadExecutor()       //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
//        Executors.newScheduledThreadPool(3)       //创建一个定长线程池，支持定时及周期性任务执行、延迟执行
        Executors.newFixedThreadPool(2)     //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    }

    override fun init() {
        binding.onClick = onClick
    }

    val onClick = View.OnClickListener {
        when (it.id) {
            //一、创建线程的四种方式
            R.id.thread_btn_thread -> threadTest()
            R.id.thread_btn_runnable -> runnableThreadTest()
            R.id.thread_btn_future_task -> futureTaskTest()
            R.id.thread_btn_executors -> executorsTest()

            //二、线程池的三种关闭方式
            R.id.thread_btn_start_executors -> startExecutors()
            R.id.thread_btn_shutdown -> shutdown()
            R.id.thread_btn_shutdown_new -> shutdownNow()
            R.id.thread_btn_awaitTermination -> awaitTermination()
        }
    }

    // region 一、线程创建的四种方式 测试
    // 1.继承Thread类
    private fun threadTest() {
        val threadTest = ThreadCreate.ThreadTest()
        threadTest.start()
    }

    // 2.实现Runnable接口，可避免Java单继承问题，依然需要Thread类
    private fun runnableThreadTest() {
        val runnableThreadTest = Thread(ThreadCreate.RunnableTest())
        runnableThreadTest.start()
    }

    // 3.FutureTask类 + Callable接口，可携带返回值，需要Thread类或者Executors
    private fun futureTaskTest() {
        val callableTest: Callable<String> = ThreadCreate.CallableTest()
        val futureTaskTest: FutureTask<String> = FutureTask<String>(callableTest)

        //第一种futureTask方式 Executors.submit(Callable)
        val executor = Executors.newCachedThreadPool()
        val submit = executor.submit(callableTest)
        LogUtils.tag("main").i("第一种Future实现方式: Executors.submit(Callable) 返回结果:${submit.get()}")
        executor.shutdown()

        //第二种futureTask方式
        Thread(futureTaskTest).start()
        LogUtils.tag("main").i("第二种Future实现方式: Thread(FutureTask) 返回结果:${futureTaskTest.get()}")
    }

    // 4.借助Executors(线程池)
    private fun executorsTest() {
        val es = Executors.newCachedThreadPool()
        es.execute { LogUtils.tag("main").i("使用 Executors 创建的线程") }
    }
    // endregion

    // region 二、线程池的三种关闭方式
    var threadName = 1
    private fun startExecutors() {
        executor.submit {
            val position = threadName++
            for (i in 1..10) {
                LogUtils.tag("main").i("Executors 创建的线程 线程名:$position 次数:$i")
                Thread.sleep(1000)
            }
        }
    }

    /*
     * 1.将线程池的状态设置为 SHUTDOWN
     * 2.不会立即停止线程池，会继续执行尚未结束的任务 & 任务队列中的任务，直到全部执行完毕
     * 3.不再接受新的任务，强行添加会报错(submit 和 execute 方式都一样) --RejectedExecutionException
     */
    private fun shutdown() {
        executor.shutdown()
    }

    /*
     * 1.将线程池的状态设置为 STOP
     * 2.正在执行的任务会被尝试 interrupt()中断，没被执行的任务则被返回，不包含正在执行的任务。
     */
    private fun shutdownNow() {
        val shutdownNow = executor.shutdownNow()
        LogUtils.tag("main").i("Executors.shutdownNow() 还未被执行的线程数量:${shutdownNow.size}")
    }

    /*
     * 主线程等各子线程都运行完毕后再执行，可应用于设置多少秒后强制销毁线程池
     * 第一个参数指定的是时间，第二个参数指定的是时间单位(当前是秒)
     *
     * 1.awaitTermination()方法所在线程会陷入阻塞状态，阻塞时间就是所设置时间（阻塞时间可以设置超过线程池本身所需的执行时间）
     *   阻塞地点就是awaitTermination()方法。
     * 2.线程池行为和shutdown()相同，只是阻塞时间内不可继续添加线程（强行添加不会抛异常），过了阻塞期后可以继续添加
     * 3.返回结果 = 阻塞结束时线程池是否已被销毁 (shutdown和shutdownNow方法能销毁线程池)
     */
    private fun awaitTermination() {
        LogUtils.tag("main").i("Executors.awaitTermination() 开始等待……")
        executor.shutdown() //shutdown之后线程池才能被termination。
        val awaitTermination = executor.awaitTermination(3, TimeUnit.SECONDS)
        LogUtils.tag("main").i("Executors.awaitTermination() 等待结束！！")
        LogUtils.tag("main").i("Executors.awaitTermination() 返回结果:${awaitTermination}")
    }
    // endregion

    // region 三、java 中的 wait 和 notify
    /*
     * https://www.cnblogs.com/jerryshao2015/p/4419638.html
     */

    // endregion
}