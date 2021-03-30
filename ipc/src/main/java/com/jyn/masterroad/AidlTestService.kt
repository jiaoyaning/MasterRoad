package com.jyn.masterroad

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import com.apkfuns.logutils.LogUtils

class AidlTestService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.tag(TAG).i("AidlTestService onStartCommand启动")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.getLogConfig().configShowBorders(false)
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

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            //可用来判断发送方的身份
            val callingPid = getCallingPid()
            val callingUid = getCallingUid()
            LogUtils.tag(TAG).i("AidlTestService 安全性保证 callingPid: $callingPid ; callingUid $callingUid")
            return super.onTransact(code, data, reply, flags)
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
            LogUtils.tag(TAG).i("AidlTestService setInOutTest 接受到的数据: $inoutTest")
            inoutTest.also {
                it.name = "AidlTestService setInOutTest"
                it.x = 10
                it.y = 10
            }
            LogUtils.tag(TAG).i("AidlTestService setInOutTest 接受并修改的数据: $inoutTest")
        }

        override fun setOutTest(outTest: AidlTestBean) {
            LogUtils.tag(TAG).i("AidlTestService outTest receive: $outTest")
            outTest.also {
                it.name = "AidlTestService setOutTest"
                it.x = 20
                it.y = 20
            }
            LogUtils.tag(TAG).i("AidlTestService outTest change: $outTest")
        }

        override fun setInTest(inTest: AidlTestBean) {
            LogUtils.tag(TAG).i("AidlTestService inTest 接受到的数据: $inTest")
            inTest.also {
                it.name = "AidlTestService inTest"
                it.x = 30
                it.y = 30
            }
            LogUtils.tag(TAG).i("AidlTestService inTest 接受并修改的数据: $inTest")
        }

        override fun onewayTest(aidlTestBean: AidlTestBean) {
            LogUtils.tag(TAG).i("AidlTestService onewayTest 接受到的数据: $aidlTestBean")
            Thread.sleep(2000)
            aidlTestBean.also {
                it.x = 40
                it.y = 40
                it.name = "AidlTestService onewayTest"
            }
            LogUtils.tag(TAG).i("AidlTestService onewayTest 接受并修改的数据: $aidlTestBean")
        }
    }
}
