package com.jyn.masterroad.handler

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.MessageQueue
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath.HANDLER
import java.lang.reflect.Method


@Route(path = HANDLER)
class HandlerActivity : AppCompatActivity() {

    private var token: Int = 0

    //重写handleMessage的handler
    var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    //自带callback的handler ，如果返回true则不会再走handleMessage方法回调
    var handlerCallback: Handler = Handler {
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        handleSyncBarrier()
    }

    private fun handleSyncBarrier() {

    }

    /**
     * 添加内存屏障
     */
    @SuppressLint("DiscouragedPrivateApi")
    private fun sendSyncBarrier() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val queue = handler.looper.queue
            val method: Method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
            method.isAccessible = true
            token = method.invoke(queue) as Int
        }
    }

    /**
     * 移除同步屏障
     */
    @SuppressLint("DiscouragedPrivateApi")
    private fun removeSyncBarrier() {
        val queue = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            handler.looper.queue
        } else {
            null
        }
        val method: Method = MessageQueue::class.java.getDeclaredMethod("removeSyncBarrier", Int::class.javaPrimitiveType)
        method.isAccessible = true
        token = method.invoke(queue) as Int
    }
}