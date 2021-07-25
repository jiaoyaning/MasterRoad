package com.jyn.masterroad.ipc.aidl

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM

/**
 * Created by jiaoyaning on 2021/7/25.
 */
class AidlClientViewModel(application: Application) : BaseVM(application) {

    var mAidlInterface: AidlInterface? = null

    /**
     * 1. 编写aidl文件
     * 2. 把aidl和所引入的bean文件 copy到server端一份 (确保路径&名字完全一致)
     * 3. bindService成功后，获取aidl对象
     * ~~ 然后就可以自由自在的调用编写好的接口了
     */

    fun bindAidlServer() {
        LogUtils.tag(TAG).i("Client端 bindService")
        val intent1 = Intent()
        intent1.setClassName(
            "com.jyn.masterroad",
            "com.jyn.masterroad.ipc.aidl.AidlServerService"
        )
        context.bindService(intent1, aidlServiceConnection, AppCompatActivity.BIND_AUTO_CREATE)

        //需要在清单文件里配置 <action /> 才行
        val intent2 = Intent("com.jyn.masterroad.ipc.IpcServerService.Action")
        intent2.setPackage("com.jyn.masterroad")
//        bindService(intent2, serviceConnection, BIND_AUTO_CREATE)

        //通过 package 和 classname
        val intent3 = Intent()
        intent3.component = ComponentName(
            "com.jyn.masterroad",
            "com.jyn.masterroad.ipc.aidl.IpcAidlServerService"
        )
//        bindService(intent3, serviceConnection, BIND_AUTO_CREATE)
    }

    private val aidlServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.tag(TAG).i("Client端 AIDL 失去链接:${name?.className}")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtils.tag(TAG).i("Client端 AIDL 链接成功:${name?.className}")
            mAidlInterface = AidlInterface.Stub.asInterface(service)
        }
    }

    fun inTest() {
        val inTest = "inTest ${System.currentTimeMillis()}"
        LogUtils.tag(AidlServerService.TAG).i("Client端 inTest:$inTest")
        mAidlInterface?.inTest(inTest)
    }

    private val outTestAidlBean: AidlBean by lazy { AidlBean(0, "outTest") }
    fun outTest() {
        LogUtils.tag(AidlServerService.TAG).i("Client端 outTest:$outTestAidlBean")
        mAidlInterface?.outTest(outTestAidlBean)
    }

    private val inoutTestAidlBean: AidlBean by lazy { AidlBean(0, "inOutTest") }
    fun inoutTest() {
        LogUtils.tag(AidlServerService.TAG).i("Client端 inoutTest:$inoutTestAidlBean")
        mAidlInterface?.inoutTest(inoutTestAidlBean)
    }

    private val onewayTestAidlBean: AidlBean by lazy { AidlBean(0, "onewayTest") }
    fun onewayTest() {
        LogUtils.tag(AidlServerService.TAG).i("Client端 onewayTest:$onewayTestAidlBean")
        mAidlInterface?.onewayTest(onewayTestAidlBean)
    }
}