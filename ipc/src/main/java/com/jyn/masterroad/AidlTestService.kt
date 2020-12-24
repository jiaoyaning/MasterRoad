package com.jyn.masterroad

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.apkfuns.logutils.LogUtils

class AidlTestService : Service() {

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

    class AidlStringTestBinder : AidlTestInterface.Stub() {
        private var innerTest = "张三"

        override fun setTest(test: String) {
            innerTest = test
        }

        override fun testFun(aidlTestBean1: AidlTestBean, aidlTestBean2: AidlTestBean): AidlTestBean {
            val aidlTestBean = AidlTestBean()
            aidlTestBean.name = (aidlTestBean1.x + aidlTestBean2.y).toString()
            return aidlTestBean
        }

        override fun getTest(): String {
            return innerTest
        }
    }
}
