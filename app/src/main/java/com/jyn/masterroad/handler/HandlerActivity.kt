package com.jyn.masterroad.handler

import android.annotation.SuppressLint
import android.os.*
import android.os.Handler.Callback
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityHandlerBinding
import java.lang.reflect.Method
import com.jyn.masterroad.handler.HandlerTest.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
 * 享元模式 TODO
 *
 * 万字总结-保姆级Handler机制解读
 * https://mp.weixin.qq.com/s/7S9NITBi3sXsqrzxNfMa8Q
 *
 * 万字图文，带你学懂Handler和内存屏障
 * https://mp.weixin.qq.com/s/tbvzs7K5OXIiAKL_DYr20A
 *
 * Android 消息屏障与异步消息
 * https://mp.weixin.qq.com/s/F9c9q0IO4FvnXVvCntL0Iw
 *
 * 介绍一下 Android Handler 中的 epoll 机制？
 * https://mp.weixin.qq.com/s/ClTE15s9qUaNsInIIwX57w
 *
 * Linux IO模式及 select、poll、epoll详解
 * https://segmentfault.com/a/1190000003063859
 *
 * 面试必备：异步 Handler 十大必问！
 * https://mp.weixin.qq.com/s/RVgGEPw7srV_uwBtlHEGXA
 *
 * 面试官：如何提高Message的优先级
 * https://mp.weixin.qq.com/s/nOcI2J6yUW-vTNy-VuZufQ
 *
 * 同步屏障与异步消息，从入门到放弃
 * https://mp.weixin.qq.com/s/f7oJfh5fArN6yPVQmJw-xA
 */
@Route(path = RoutePath.Handle.path)
@SuppressLint("NewApi", "DiscouragedPrivateApi", "HandlerLeak")
class HandlerActivity : BaseActivity<ActivityHandlerBinding>
(R.layout.activity_handler) {

    private val handlerTest: HandlerTest by lazy { HandlerTest() }

    override fun initView() {
        binding.handler = handlerTest
    }

//    private var token: Int = 0
//    /**
//     * 添加内存屏障
//     */
//    private fun sendSyncBarrier() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val queue = normalHandler.looper.queue
//            val method: Method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
//            method.isAccessible = true
//            token = method.invoke(queue) as Int
//        }
//    }
//
//    /**
//     * 移除同步屏障
//     */
//    private fun removeSyncBarrier() {
//        val queue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            normalHandler.looper.queue
//        } else {
//            null
//        }
//        val method: Method = MessageQueue::class.java.getDeclaredMethod("removeSyncBarrier", Int::class.javaPrimitiveType)
//        method.isAccessible = true
//        token = method.invoke(queue) as Int
//    }
}