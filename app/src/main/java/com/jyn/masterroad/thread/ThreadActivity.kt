package com.jyn.masterroad.thread

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityThreadBinding

/*
 * JDK线程池源码分析之ThreadPoolExecutor
 * https://www.jianshu.com/p/072703367564
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
 *
 * 面试老爱问的线程池，一把梭哈！
 * https://mp.weixin.qq.com/s/b2PUpprFFWGthZyGDd8g-g（思路强）
 *
 * 让线程按顺序执行8种方法
 * https://cnblogs.com/wenjunwei/p/10573289.html
 *
 * 【236期】面试官：线程池中多余的线程是如何回收的？
 * https://mp.weixin.qq.com/s/HlHps1T-8MqE458IDxAkQg
 *
 * 我会手动创建线程，为什么让我使用线程池？
 * https://mp.weixin.qq.com/s/oYkeQSjEtonjzjTA1hR07w
 */
@Route(path = RoutePath.Thread.path)
class ThreadActivity : BaseActivity<ActivityThreadBinding>() {

    override fun getLayoutId() = R.layout.activity_thread

    private val threadLocalTest: ThreadLocalTest by lazy { ThreadLocalTest() }
    private val countDownLatchTest: CountDownLatchTest by lazy { CountDownLatchTest() }

    override fun initView() {
        binding.onClick = onClick
        binding.threadCreate = ThreadCreate()
        binding.executorsTest = ExecutorsTest()
        binding.threadWaitNotifyTest = ThreadWaitNotifyTest()
    }

    val onClick = View.OnClickListener {
        when (it.id) {
            //四、ThreadLocal
            R.id.thread_btn_thread_local -> threadLocalTest.threadLocalTest()
            R.id.thread_btn_thread_local_inner -> threadLocalTest.threadLocalInnerTest()
            R.id.thread_btn_inheritable_thread_local -> threadLocalTest.inheritableThreadLocalTest()

            //五、CountDownLatch
            R.id.thread_btn_latch_demo -> countDownLatchTest.demo()
            R.id.thread_btn_latch_time_out -> countDownLatchTest.awaitTimeOutTest()
        }
    }
}