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

/*
 * 万字总结-保姆级Handler机制解读
 * https://mp.weixin.qq.com/s/7S9NITBi3sXsqrzxNfMa8Q
 *
 * 想要发送Message共有3种用法：
 * 1、实例化默认Handler并向Message添加Runnable
 * 2、实例化默认Handler并通过构造函数传入接口Handler.Callback实例
 * 3、继承Handler重写handleMessage()方法
 *
 * 当这3种实现方式同时存在的时候，遵循优先级：
 * Message.callback > Callback.handleMessage > handleMessage
 *
 * 面试官：“看你简历上写熟悉 Handler 机制，那聊聊 IdleHandler 吧？”
 * https://mp.weixin.qq.com/s/mR7XIVbaKsB4q-Rxe1ip2g
 *
 * IdleHandler是一个回调接口，可以通过MessageQueue的addIdleHandler添加实现类。
 * 当MessageQueue中的任务暂时处理完了（没有新任务或者下一个任务延时在之后），这个时候会回调这个接口.
 * 返回false，那么就会移除它，返回true就会在下次message处理完了的时候继续回调。
 *
 * 享元模式 TODO
 *
 * Android的消息机制，一文吃透
 * https://juejin.cn/post/6939425097069363230
 *
 * Handler同步屏障
 * https://mp.weixin.qq.com/s/3H-aQd_jsqTBWslqX_BfLA
 */
@Route(path = RoutePath.Handle.path)
@SuppressLint("NewApi", "DiscouragedPrivateApi", "HandlerLeak")
class HandlerActivity : BaseActivity<ActivityHandlerBinding>
(R.layout.activity_handler) {

    private val handlerTest: HandlerTest by lazy {
        HandlerTest().apply { handler = this@HandlerActivity.handler }
    }

    var handler: Handler = object : Handler(Callback {
        LogUtils.tag(TAG).i("这是Handler的Callback形参 obj:${it.obj}")
        return@Callback false //返回true的时候，不会再执行Handler的handleMessage方法
    }) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            LogUtils.tag(TAG).i("这是重写了Handler的handleMessage方法 obj:${msg.obj}")
        }
    }

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