package com.jyn.masterroad.Thread

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.utils.ARouter.RoutePath
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

    //1.继承Thread类
    private val threadTest: ThreadCreate.ThreadTest by lazy { ThreadCreate.ThreadTest() }

    //2.实现Runnable接口，可避免Java单继承问题，依然需要Thread类
    private val runnableThreadTest: Thread by lazy { Thread(ThreadCreate.RunnableTest()) }

    //3.FutureTask类 + Callable接口，可携带返回值，需要Thread类或者Executors
    private val callableTest: Callable<String> by lazy { ThreadCreate.CallableTest() }
    private val futureTaskTest: FutureTask<String> by lazy { FutureTask<String>(callableTest) }

    override fun getLayoutId(): Int {
        return R.layout.activity_thread
    }

    override fun init() {
        binding.onClick = onClick
    }

    val onClick = View.OnClickListener {
        when (it.id) {
            R.id.thread_btn_thread -> threadTest.start()
            R.id.thread_btn_runnable -> runnableThreadTest.start()
            R.id.thread_btn_future_task -> futureTaskTest()
        }
    }

    /**
     *
     */
    private fun futureTaskTest() {
        //第一种方式 Executors.submit(Callable)
        val executor = Executors.newCachedThreadPool()
        val submit = executor.submit(callableTest)
        LogUtils.tag("main").i("第一种Future实现方式: Executors.submit(Callable) 返回结果:$submit")
        executor.shutdown()

        //第二种方式
        Thread(futureTaskTest).start()
        LogUtils.tag("main").i("第二种Future实现方式: Thread(FutureTask) 返回结果:$futureTaskTest.get()")
    }
}