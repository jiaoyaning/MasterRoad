package com.jyn.masterroad.handler

import android.os.Handler
import android.os.Message
import android.view.View
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils
import javax.security.auth.callback.Callback

class HandlerTest : ViewModel() {
    companion object {
        const val TAG = "Handler"
    }

    var handler: Handler? = null

    //带callback的message
    fun messageWithCallback(v: View) {
        LogUtils.tag(TAG).i("--> 带callback的message")
        val message = Message.obtain(handler) {
            LogUtils.tag(TAG).i("这是重写了 Runnable 的 Message ")
        }
        message.obj = "带callback的Message"
        handler?.handleMessage(message)
    }

    //不带callback的message
    fun messageNotCallback(v: View) {
        LogUtils.tag(TAG).i("--> 不带callback的message")
        val obtainMessage = handler?.obtainMessage()
        obtainMessage?.let {
            it.obj = "普普通通Message"
            handler?.sendMessage(obtainMessage)
        }
    }
}