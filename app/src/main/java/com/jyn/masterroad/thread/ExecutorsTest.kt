package com.jyn.masterroad.thread

import android.view.View
import com.apkfuns.logutils.LogUtils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/*
 * 线程池关闭的几种方式
 *
 * Java线程池是如何保证核心线程不被销毁的
 * https://blog.csdn.net/smile_from_2015/article/details/105259789
 */
class ExecutorsTest @Inject constructor() {

    private val executor: ExecutorService by lazy {
        Executors.newCachedThreadPool()                 //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
        Executors.newSingleThreadExecutor()             //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
        Executors.newScheduledThreadPool(2)  //创建一个定长线程池，支持定时及周期性任务执行、延迟执行
        Executors.newFixedThreadPool(2)         //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    }

    fun startExecutors(v:View) {
        executor.submit {
            for (i in 1..10) {
                LogUtils.tag("main").i("Executors 创建的线程  次数:$i")
                Thread.sleep(1000)
            }
        }
    }

    // region 一、线程池关闭
    /*
     * 1.将线程池的状态设置为 SHUTDOWN
     * 2.不会立即停止线程池，会继续执行尚未结束的任务 & 任务队列中的任务，直到全部执行完毕
     * 3.不再接受新的任务，强行添加会报错(submit 和 execute 方式都一样) --RejectedExecutionException
     */
    fun shutdown(v:View) {
        executor.shutdown()
    }

    /*
     * 1.将线程池的状态设置为 STOP
     * 2.正在执行的任务会被尝试 interrupt()中断，没被执行的任务则被返回，不包含正在执行的任务。
     */
    fun shutdownNow(v:View) {
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
    fun awaitTermination(v: View) {
        LogUtils.tag("main").i("Executors.awaitTermination() 开始等待……")
        executor.shutdown() //shutdown之后线程池才能被termination。
        val awaitTermination: Boolean = executor.awaitTermination(3, TimeUnit.SECONDS)
        LogUtils.tag("main").i("Executors.awaitTermination() 等待结束！！")
        LogUtils.tag("main").i("Executors.awaitTermination() 返回结果:${awaitTermination}")
    }
    // endregion
}