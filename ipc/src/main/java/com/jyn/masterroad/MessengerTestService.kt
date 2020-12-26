package com.jyn.masterroad

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.*
import com.apkfuns.logutils.LogUtils

class MessengerTestService : Service() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.getLogConfig().configShowBorders(false)
        LogUtils.tag(TAG).i("MessengerTestService onCreate启动")
    }

    override fun onBind(intent: Intent): IBinder {
        return messenger.binder
    }

    @SuppressLint("HandlerLeak")
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            LogUtils.tag(TAG).i("MessengerTestService 接受到消息：$msg")
            val obtain = Message.obtain()
            obtain.data = Bundle().also {
                it.putString("key", "MessengerTestService 发出的消息")
            }
            msg.replyTo.send(obtain)
        }
    }

    private val messenger = Messenger(handler)
}