package com.jyn.masterroad.handler

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityHandlerBinding
import java.lang.Thread.sleep
import kotlin.concurrent.thread

/*
 * 享元模式
 *
 * 同步屏障与异步消息:[HandlerSyncBarrier]
 *
 * 介绍一下 Android Handler 中的 epoll 机制？
 * https://mp.weixin.qq.com/s/ClTE15s9qUaNsInIIwX57w
 * 图解：epoll怎么实现的
 * https://mp.weixin.qq.com/s/nTuTJuWVZmhpiEfckmZ1Gg
 * https://zhuanlan.zhihu.com/p/272891398
 *
 * 万字总结-保姆级Handler机制解读
 * https://mp.weixin.qq.com/s/7S9NITBi3sXsqrzxNfMa8Q
 *
 * 万字图文，带你学懂Handler和内存屏障
 * https://mp.weixin.qq.com/s/tbvzs7K5OXIiAKL_DYr20A
 *
 * 面试必备：异步 Handler 十大必问！
 * https://mp.weixin.qq.com/s/RVgGEPw7srV_uwBtlHEGXA
 *
 * 面试官：如何提高Message的优先级
 * https://mp.weixin.qq.com/s/nOcI2J6yUW-vTNy-VuZufQ
 *
 * Handler 27问
 * https://juejin.cn/post/6943048240291905549
 *
 * 小题大做 | 内存泄漏简单问，你能答对吗
 * https://juejin.cn/post/6909362503898595342
 */
@Route(path = RoutePath.Handle.path)
@SuppressLint("NewApi", "DiscouragedPrivateApi", "HandlerLeak", "SetTextI18n")
class HandlerActivity : BaseActivity<ActivityHandlerBinding>(R.layout.activity_handler) {

    private val handlerCreateTest: HandlerCreateTest by lazy { HandlerCreateTest() }
    private val handlerSyncBarrier: HandlerSyncBarrier by lazy { HandlerSyncBarrier() }

    private var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            binding.handlerTvLeak.text = "我有没有内存泄漏 handleMessage"
            LogUtils.tag("Handler").i(binding.handlerTvLeak.text)
        }
    }

    private val runnable = {
        binding.handlerTvLeak.text = "我有没有内存泄漏 runnable"
        LogUtils.tag("Handler").i(binding.handlerTvLeak.text)
    }

    override fun initView() {
        binding.handlerCreate = handlerCreateTest
        binding.handlerSyncBarrier = handlerSyncBarrier

        binding.handlerLeakBtnDelay.setOnClickListener { postDelayed() }
        binding.handlerLeakBtnDelayMessage.setOnClickListener { sendMessageDelayed() }
        binding.handlerLeakBtnThread.setOnClickListener { newThread() }
    }

    /*
     * 1、发送延迟消息
     */
    private fun postDelayed() {
        handler.postDelayed(runnable, 5000)
        finish()
    }

    private fun sendMessageDelayed() {
        handler.sendEmptyMessageDelayed(0, 4000)
        finish()
    }

    /*
     * 2、子线程运行没结束
     */
    private fun newThread() {
        thread {
            sleep(5000)
            handler.post(runnable)
        }
        finish()
    }

    /**
     * 对象被回收的时候会调用该方法
     */
    protected fun finalize() {
        LogUtils.tag("Handler").i("finalize -> Activity被回收")
    }
}