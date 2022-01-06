package com.jyn.masterroad.utils.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.apkfuns.logutils.LogUtils

/**
 * 预处理服务意思是在路由navigation之前进行干扰路由
 */
@Route(path = "/app/pretreatmentService")
class PretreatmentServiceImpl : PretreatmentService {
    companion object {
        const val TAG = "ARouter"
    }

    override fun init(context: Context?) {
        LogUtils.tag(TAG).i("PretreatmentServiceImpl --> init")
    }

    override fun onPretreatment(context: Context?, postcard: Postcard?): Boolean {
        if (postcard?.path == "/app/main") {
            LogUtils.tag(TAG).i("PretreatmentServiceImpl --> 主页没有路由")
            return false // 跳转前预处理，如果需要自行处理跳转，该方法返回 false 即可
        }
        return true
    }
}