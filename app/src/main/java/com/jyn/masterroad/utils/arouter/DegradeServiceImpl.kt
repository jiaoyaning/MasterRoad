package com.jyn.masterroad.utils.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.apkfuns.logutils.LogUtils

/**
 * Route注解定义了全局降级策略
 */
@Route(path = "/app/DegradeServiceImpl")
class DegradeServiceImpl : DegradeService {
    companion object {
        const val TAG = "ARouter"
    }

    override fun init(context: Context?) {
        LogUtils.tag(TAG).i("DegradeServiceImpl --> init")
    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        LogUtils.tag(TAG).i("DegradeServiceImpl --> 没有找到该路由地址:${postcard?.path}")
    }
}