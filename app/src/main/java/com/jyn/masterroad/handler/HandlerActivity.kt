package com.jyn.masterroad.handler

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityHandlerBinding

/**
 * 享元模式
 *
 * 同步屏障与异步消息:[HandlerSyncBarrier]
 *
 * 万字总结-保姆级Handler机制解读
 * https://mp.weixin.qq.com/s/7S9NITBi3sXsqrzxNfMa8Q
 *
 * 万字图文，带你学懂Handler和内存屏障
 * https://mp.weixin.qq.com/s/tbvzs7K5OXIiAKL_DYr20A
 *
 * 介绍一下 Android Handler 中的 epoll 机制？
 * https://mp.weixin.qq.com/s/ClTE15s9qUaNsInIIwX57w
 *
 * Linux IO模式及 select、poll、epoll详解
 * https://segmentfault.com/a/1190000003063859
 *
 * select、poll、epoll之间的区别(搜狗面试)
 * https://www.cnblogs.com/aspirant/p/9166944.html
 *
 * 面试必备：异步 Handler 十大必问！
 * https://mp.weixin.qq.com/s/RVgGEPw7srV_uwBtlHEGXA
 *
 * 面试官：如何提高Message的优先级
 * https://mp.weixin.qq.com/s/nOcI2J6yUW-vTNy-VuZufQ
 */
@Route(path = RoutePath.Handle.path)
@SuppressLint("NewApi", "DiscouragedPrivateApi", "HandlerLeak")
class HandlerActivity : BaseActivity<ActivityHandlerBinding>
    (R.layout.activity_handler) {

    private val handlerCreateTest: HandlerCreateTest by lazy { HandlerCreateTest() }
    private val handlerSyncBarrier: HandlerSyncBarrier by lazy { HandlerSyncBarrier() }

    override fun initView() {
        binding.handlerCreate = handlerCreateTest
        binding.handlerSyncBarrier = handlerSyncBarrier
    }
}