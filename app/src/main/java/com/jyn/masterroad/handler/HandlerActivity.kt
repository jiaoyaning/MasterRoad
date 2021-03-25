package com.jyn.masterroad.handler

import android.annotation.SuppressLint
import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityHandlerBinding
import java.lang.reflect.Method

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
 * Message.callback > Callback.handleMessage > handleMessage：
 *
 * 面试官：“看你简历上写熟悉 Handler 机制，那聊聊 IdleHandler 吧？”
 * https://mp.weixin.qq.com/s/mR7XIVbaKsB4q-Rxe1ip2g
 *
 * IdleHandler是一个回调接口，可以通过MessageQueue的addIdleHandler添加实现类。
 * 当MessageQueue中的任务暂时处理完了（没有新任务或者下一个任务延时在之后），这个时候会回调这个接口.
 * 返回false，那么就会移除它，返回true就会在下次message处理完了的时候继续回调。
 *
 * 享元模式 TODO
 */
@Route(path = RoutePath.Handle.path)
@SuppressLint("NewApi", "DiscouragedPrivateApi")
class HandlerActivity : BaseActivity<ActivityHandlerBinding>(R.layout.activity_handler) {

    private var token: Int = 0

    //1. 一个平平无奇的handler
    private val normalHandler = Handler(Looper.getMainLooper())

    //2. 重写handleMessage的handler
    @SuppressLint("HandlerLeak")
    var handleMessage: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            LogUtils.tag("Handler").i("这是一个重写了handleMessage的Handler")
        }
    }

    //3. 自带callback的handler ，如果返回true则不会再走handleMessage方法回调
    var handlerCallback: Handler = Handler(Handler.Callback {
        LogUtils.tag("Handler").i("这是一个重写了Callback的Handler")
        return@Callback true
    })

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sendMassage()
        }
    }

    //向Message添加Callback

    private fun sendMassage() {
        //一个自带callback的message
        val message = Message.obtain(normalHandler) {
            LogUtils.tag("Handler").i("这是一个重写了Runnable的Message")
        }

        //1. 带callback的Message优先级最高
        normalHandler.sendMessageDelayed(message, 5000)
        //2. 重写handlerMessage方法的Handler优先级第三
        handleMessage.sendEmptyMessageDelayed(10, 5000)
        //3. 带callback的Handler优先级第二
        handlerCallback.sendEmptyMessageDelayed(10, 5000)

        /**
         * 就算是返回了true也不是一直运行，而是在MessageQueue.next的时候执行
         * 一次next只执行一次，等下一次next的时候如果还返回true就会再执行一遍。
         *
         * false:只执行一遍
         */
        normalHandler.looper.queue.addIdleHandler(object : MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                LogUtils.tag("Handler").i("这是一个IdleHandler")
                return false
            }
        })
    }


    /**
     * 添加内存屏障
     */
    private fun sendSyncBarrier() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val queue = normalHandler.looper.queue
            val method: Method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
            method.isAccessible = true
            token = method.invoke(queue) as Int
        }
    }

    /**
     * 移除同步屏障
     */
    private fun removeSyncBarrier() {
        val queue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            normalHandler.looper.queue
        } else {
            null
        }
        val method: Method = MessageQueue::class.java.getDeclaredMethod("removeSyncBarrier", Int::class.javaPrimitiveType)
        method.isAccessible = true
        token = method.invoke(queue) as Int
    }
}