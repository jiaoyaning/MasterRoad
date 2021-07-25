package com.jyn.masterroad.ipc

import android.app.ActivityManager.TaskDescription
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityIpcClientBinding
import com.jyn.masterroad.ipc.aidl.AidlClientViewModel

/*
 * AIDL官方文档：https://developer.android.com/guide/components/aidl?hl=zh-cn
 *
 * 同一个app不同activity显示多任务（仿微信小程序切换效果）
 * https://www.jianshu.com/p/a8f695841008
 *
 * Android——使用Binder实现进程间通讯简单案例
 * https://blog.csdn.net/m0_37602827/article/details/108656669
 */
@Route(path = RoutePath.IpcClient.path)
class IpcClientActivity : BaseActivity<ActivityIpcClientBinding>(R.layout.activity_ipc_client) {
    private val viewModel: AidlClientViewModel by viewModels()

    override fun initView() {
        setTaskDescription(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                TaskDescription("IpcClient端", R.mipmap.icon_master_road)
            else TaskDescription("IpcClient端")
        )
        binding.clientVM = viewModel
    }
}