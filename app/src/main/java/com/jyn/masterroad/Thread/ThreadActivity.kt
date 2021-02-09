package com.jyn.masterroad.Thread

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityThreadBinding
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

/*
 * JDK线程池源码分析之ThreadPoolExecutor
 * https://www.jianshu.com/p/072703367564
 *
 * ExecutorService 的 shutdown 和 shutdownNow 区别与联系
 * https://www.jianshu.com/p/f2591bdd0877
 *
 * 线程攻略，夯实基础很重要！
 * https://juejin.cn/post/6866834999081959438
 */
@Route(path = RoutePath.Thread.path)
class ThreadActivity : BaseActivity<ActivityThreadBinding>() {

    override fun getLayoutId() = R.layout.activity_thread

    override fun init() {
        binding.onClick = onClick
    }

    val onClick = View.OnClickListener {
        when (it.id) {
            R.id.thread_btn_thread -> threadTest()
            R.id.thread_btn_runnable -> runnableThreadTest()
            R.id.thread_btn_future_task -> futureTaskTest()
        }
    }

    /*
     * 1.继承Thread类
     */
    private fun threadTest() {
        val threadTest = ThreadCreate.ThreadTest()
        threadTest.start()
    }

    /*
     * 2.实现Runnable接口，可避免Java单继承问题，依然需要Thread类
     */
    private fun runnableThreadTest() {
        val runnableThreadTest = Thread(ThreadCreate.RunnableTest())
        runnableThreadTest.start()
    }

    /**
     *  3.FutureTask类 + Callable接口，可携带返回值，需要Thread类或者Executors
     */
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
}