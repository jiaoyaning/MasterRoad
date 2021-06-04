package com.jyn.masterroad.handler

import android.annotation.SuppressLint
import android.os.*
import androidx.annotation.RequiresApi
import com.apkfuns.logutils.LogUtils

/*
 * 同步屏障与异步消息，从入门到放弃
 * https://mp.weixin.qq.com/s/f7oJfh5fArN6yPVQmJw-xA
 *
 * Android 消息屏障与异步消息
 * https://mp.weixin.qq.com/s/F9c9q0IO4FvnXVvCntL0Iw
 */
@SuppressLint("DiscouragedPrivateApi")
class HandlerSyncBarrier {
    companion object {
        const val TAG = "Handler"
    }

    /*
     * 为了提升优先级
     *  1. MessageQueue.next()时，发现Message的target变量为null时，触发同步屏障，阻止后续的同步消息执行。
     *  2. 判断Message的isAsynchronous()方法，true时为异步消息优先处理，false时为同步消息。
     *
     *  PS: 这里同步和异步仅仅为一个标识，并不是类似并发的概念
     */
    var handler: Handler = Handler(Looper.getMainLooper())

    //同步延迟消息
    fun postDelayed() {
        LogUtils.tag(TAG).i("postDelayed ——> 发送同步延迟消息")
        handler.postDelayed({
            LogUtils.tag(TAG).i("我是同步消息，延迟 1 秒发送")
        }, 1000)
    }

    fun syncMessage() {
        LogUtils.tag(TAG).i("syncMessage ——> 发送异步延迟消息")
        val message = Message.obtain(handler) {
            LogUtils.tag(TAG).i("我是异步消息，延迟 3 秒发送")
            removeSyncBarrier()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            message.isAsynchronous = true
        }
        handler.sendMessageDelayed(message, 3000)
    }


    private var token: Int = 0

    /**
     * postSyncBarrier 发送一个同步屏障
     */
    fun sendSyncBarrier() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val queue = handler.looper.queue
            val method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
            method.isAccessible = true
            token = method.invoke(queue) as Int
            LogUtils.tag(TAG).i("token ——> $token")
        }
    }

    /**
     * removeSyncBarrier 移除同步屏障
     */
    private fun removeSyncBarrier() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val queue = handler.looper.queue
            val method = MessageQueue::class.java
                .getDeclaredMethod("removeSyncBarrier", Int::class.java)
            method.isAccessible = true
            method.invoke(queue, token)
        }
    }
}