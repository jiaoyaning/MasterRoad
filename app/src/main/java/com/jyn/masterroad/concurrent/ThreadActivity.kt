package com.jyn.masterroad.concurrent

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.concurrent.lock.SyncAndLockTest
import com.jyn.masterroad.concurrent.thread.*
import com.jyn.masterroad.databinding.ActivityThreadBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
 *
 * 线程攻略，夯实基础很重要！
 * https://juejin.cn/post/6866834999081959438
 *
 * synchronized 的使用场景和原理简介
 * https://www.cnblogs.com/54chensongxia/p/11899031.html
 *
 * Thread.yield()和Thread.sleep(0)
 * https://blog.csdn.net/qq_15037231/article/details/103440060
 * https://blog.csdn.net/u013218720/article/details/21947613
 *
 * 一文掌握 Thread 类中 yield 方法具体作用
 * https://mp.weixin.qq.com/s/IzODJ9PZs2atKtxQPDCAZg
 *
 * 让线程按顺序执行8种方法
 * https://cnblogs.com/wenjunwei/p/10573289.html
 *
 * 深入分析 java 8 编程语言规范：Threads and Locks //TODO
 * https://javadoop.com/post/Threads-And-Locks-md
 *
 * Java线程池实现原理及其在美团业务中的实践
 * https://mp.weixin.qq.com/s/baYuX8aCwQ9PP6k7TDl2Ww
 *
 * 漫话：如何给女朋友解释为什么Java线程没有Running状态？(优质)
 * https://mp.weixin.qq.com/s/JrpIVgXq1Jqg0weO4E1mzQ
 */
@AndroidEntryPoint
@Route(path = RoutePath.Thread.path)
class ThreadActivity : BaseActivity<ActivityThreadBinding>
    (R.layout.activity_thread) {

    @Inject
    lateinit var threadCreate: ThreadCreate

    @Inject
    lateinit var executorsTest: ExecutorsTest

    @Inject
    lateinit var threadWaitNotifyTest: WaitAndNotifyTest

    @Inject
    lateinit var threadLocalTest: ThreadLocalTest

    @Inject
    lateinit var countDownLatchTest: CountDownLatchTest

    override fun initView() {
        binding.threadCreate = threadCreate
        binding.executors = executorsTest
        binding.threadWaitNotify = threadWaitNotifyTest
        binding.threadLocal = threadLocalTest
        binding.countDownLatch = countDownLatchTest
        binding.syncAndLockTest = SyncAndLockTest()
        binding.stop = ThreadStop()
    }
}