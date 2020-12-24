package com.jyn.masterroad

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.apkfuns.logutils.LogUtils

class AidlStringTestService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.tag(TAG).i("AidlStringTestService onStartCommand启动")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.tag(TAG).i("AidlStringTestService onCreate启动")
    }

    override fun onBind(intent: Intent): IBinder {
        return AidlStringTestBinder()
    }

    class AidlStringTestBinder : StringTestAidlInterface.Stub() {
        var innerTest = "张三"

        override fun setTest(test: String) {
            innerTest = test
        }

        override fun getTest(): String {
            return innerTest
        }
    }
}
