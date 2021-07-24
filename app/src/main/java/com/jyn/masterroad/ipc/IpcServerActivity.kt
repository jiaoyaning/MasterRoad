package com.jyn.masterroad.ipc

import android.app.ActivityManager.TaskDescription
import android.os.Build
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityIpcServerBinding

@Route(path = RoutePath.IpcServer.path)
class IpcServerActivity : BaseActivity<ActivityIpcServerBinding>(R.layout.activity_ipc_server) {
    override fun initView() {
        setTaskDescription(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                TaskDescription("IpcClient端", R.mipmap.icon_master_road)
            else TaskDescription("IpcClient端")
        )
    }
}