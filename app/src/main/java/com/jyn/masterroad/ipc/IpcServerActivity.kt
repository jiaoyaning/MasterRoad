package com.jyn.masterroad.ipc

import android.app.ActivityManager.TaskDescription
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.common.Base.BaseActivity
import com.jyn.masterroad.databinding.ActivityIpcServerBinding

@Route(path = RoutePath.IpcServer.path)
class IpcServerActivity : BaseActivity<ActivityIpcServerBinding>(R.layout.activity_ipc_server) {
    companion object {
        const val TAG = "aidl"
    }

    override fun initView() = setTaskDescription(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            TaskDescription("IpcServer", R.mipmap.icon_master_road)
        else TaskDescription("IpcServer端")
    )

    override fun initData() = initService()

    /**
     * 先启动aidl_test进程，模仿server端
     */
    private fun initService() {
        val intent1 = Intent()
        intent1.setClassName(
            "com.jyn.masterroad",
            "com.jyn.masterroad.ipc.aidl.IpcAidlServerService"
        )
        bindService(intent1, aidlServiceConnection, BIND_AUTO_CREATE)

        //需要在清单文件里配置 <action /> 才行
//        val intent2 = Intent("com.jyn.masterroad.ipc.IpcServerService.Action")
//        intent2.setPackage("com.jyn.masterroad")
//        bindService(intent2, serviceConnection, BIND_AUTO_CREATE)

        //通过 package 和 classname
//        val intent3 = Intent()
//        intent3.component = ComponentName("com.jyn.masterroad","com.jyn.masterroad.AidlTestService")
//        bindService(intent3, serviceConnection, BIND_AUTO_CREATE)
    }

    private val aidlServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.tag(TAG).i("Service AIDL 失去链接:${name?.className}")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtils.tag(TAG).i("Service AIDL 链接成功:${name?.className}")
        }
    }
}