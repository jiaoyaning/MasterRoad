package com.jyn.masterroad.ipc.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import com.apkfuns.logutils.LogUtils

/**
 * 绑定服务概览
 * https://developer.android.com/guide/components/bound-services?hl=zh-cn#Binder
 */
class AidlServerService : Service() {
    companion object {
        const val TAG = "aidl"
    }

    override fun onBind(intent: Intent?): IBinder {
        return AidlInterfaceIBinder()
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.getLogConfig().configShowBorders(false)
        LogUtils.tag(TAG).i("AidlServer onCreate启动")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.tag(TAG).i("AidlServer onStartCommand启动")
        return super.onStartCommand(intent, flags, startId)
    }

    class AidlInterfaceIBinder : AidlInterface.Stub() {

        //可用来判断发送方的身份
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            LogUtils.tag(TAG).i(
                "Server端 AidlServerService 安全性保证\n" +
                        "CallingPid: ${getCallingPid()}\n" +
                        "CallingUid: ${getCallingUid()}"
            )
            return super.onTransact(code, data, reply, flags)
        }

        override fun inTest(inTest: String?) {
            LogUtils.tag(TAG).i("Server端 inTest:$inTest")
        }

        override fun outTest(outTest: AidlBean?) {
            LogUtils.tag(TAG).i("Server端 outTest:$outTest")
        }

        override fun inoutTest(inoutTest: AidlBean?) {
            LogUtils.tag(TAG).i("Server端 inoutTest:$inoutTest")
        }

        override fun onewayTest(AidlBean: AidlBean?) {
            LogUtils.tag(TAG).i("Server端 AidlBean:$AidlBean")
        }
    }
}