package com.jyn.masterroad.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils
import javax.security.auth.callback.Callback

class HandlerTest : ViewModel() {
    companion object {
        const val TAG = "Handler"
    }

    //自带callback，并且重写了
    var handler: Handler = object : Handler(Looper.getMainLooper(), Callback {
        LogUtils.tag(TAG).i("这是Handler的Callback形参 obj:${it.obj}")
        //返回true的时候，不会再执行Handler的handleMessage方法
        return@Callback false
    }) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            LogUtils.tag(TAG).i("这是重写了Handler的handleMessage方法 obj:${msg.obj}")
        }
    }

    //带callback的message
    fun messageWithCallback() {
        LogUtils.tag(TAG).i("--> 带callback的message")
        val message = Message.obtain(handler) {
            LogUtils.tag(TAG).i("这是重写了 Runnable 的 Message ")
        }
        message.obj = "带callback的Message"
        handler.handleMessage(message)
    }

    //不带callback的message
    fun messageNotCallback() {
        LogUtils.tag(TAG).i("--> 不带callback的message")
        val obtainMessage = handler.obtainMessage()
        obtainMessage.let {
            it.obj = "普普通通Message"
            handler.sendMessage(obtainMessage)
        }
    }
}