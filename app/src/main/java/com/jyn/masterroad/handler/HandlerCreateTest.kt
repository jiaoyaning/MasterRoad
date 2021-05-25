package com.jyn.masterroad.handler

import android.os.Handler
import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils

class HandlerCreateTest : ViewModel() {
    companion object {
        const val TAG = "Handler"
    }

    //region 一、handler三种使用方式

    //region 1. 自带callback，并且重写了handleMessage方法
    val callback = Callback {
        LogUtils.tag(TAG).i("这是Handler的Callback形参 obj:${it.obj}")
        false // 返回true的时候，不会再执行Handler的handleMessage方法
    }
    var handler: Handler = object : Handler(Looper.getMainLooper(), callback) {
        override fun handleMessage(msg: Message) {
            LogUtils.tag(TAG).i("这是重写了Handler的handleMessage方法 obj:${msg.obj}")
        }
    }
    //endregion

    //region 2. 带callback的message
    fun messageWithCallback() {
        LogUtils.tag(TAG).i("--> 带callback的message")
        val message = Message.obtain(handler) {
            LogUtils.tag(TAG).i("这是重写了 Runnable 的 Message ")
        }
        message.obj = "带callback的Message"
        handler.sendMessage(message)
    }
    //endregion

    //region 3. 不带callback的message
    fun messageNotCallback() {
        LogUtils.tag(TAG).i("--> 不带callback的message")
        val obtainMessage = handler.obtainMessage()
        obtainMessage.let {
            it.obj = "普普通通Message"
            handler.sendMessage(obtainMessage)
        }
    }
    //endregion

    //endregion
}