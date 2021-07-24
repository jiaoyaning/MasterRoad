package com.jyn.masterroad.ipc

import android.app.ActivityManager.TaskDescription
import android.os.Build
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityIpcClientBinding

/*
 * AIDL官方文档：https://developer.android.com/guide/components/aidl?hl=zh-cn
 *
 * 同一个app不同activity显示多任务（仿微信小程序切换效果）
 * https://www.jianshu.com/p/a8f695841008
 */
@Route(path = RoutePath.IpcClient.path)
class IpcClientActivity : BaseActivity<ActivityIpcClientBinding>(R.layout.activity_ipc_client) {
    override fun initView() {
        setTaskDescription(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                TaskDescription("IpcClient端", R.mipmap.icon_master_road)
            else TaskDescription("IpcClient端")
        )
    }
}