package com.jyn.masterroad.ipc

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityIpcBinding

@Route(path = RoutePath.IPC.path)
class IpcActivity : BaseActivity<ActivityIpcBinding>(R.layout.activity_ipc) {

}