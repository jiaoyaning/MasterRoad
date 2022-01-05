package com.jyn.masterroad.handler

import android.os.Handler
import android.os.Looper
import com.apkfuns.logutils.LogUtils

class HandlerThreadTest {
    companion object {
        const val TAG = "Handler"
    }

    var handler: Handler = Handler(Looper.getMainLooper())

    fun postMessageDelayed() {
        handler.postDelayed({
            LogUtils.tag(TAG).i("postDelayed ——> 延迟消息")
        }, 5000)
    }
}