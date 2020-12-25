package com.jyn.masterroad

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.apkfuns.logutils.LogUtils

class AidlTestService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.tag(TAG).i("AidlTestService onStartCommand启动")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.tag(TAG).i("AidlTestService onCreate启动")
    }

    override fun onBind(intent: Intent): IBinder {
        return AidlStringTestBinder()
    }

    class AidlStringTestBinder : AidlTestInterface.Stub() {
        private var innerTest = "AidlTestService 初始值"

        private var bean = AidlTestBean().also {
            it.x = 0
            it.y = 0
            it.name = "AidlTestService 初始值"
        }

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

        override fun getTestBean(): AidlTestBean {
            return bean
        }

        override fun setInOutTest(inoutTest: AidlTestBean) {
            bean = inoutTest
        }

        override fun setOutTest(outTest: AidlTestBean) {
            bean = outTest
        }

        override fun setInTest(inTest: AidlTestBean) {
            bean = inTest
        }
    }
}
