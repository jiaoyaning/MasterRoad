package com.jyn.masterroad.utils.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils

/**
 * Route注解实现提服务
 */
@Route(path = "/app/HelloService", name = "测试服务")
class HelloServiceImpl : HelloService {
    companion object {
        const val TAG = "ARouter"
    }

    override fun sayHello(name: String): String {
        LogUtils.tag(TAG).i("HelloServiceImpl --> hello, $name")
        return "hello, $name"
    }

    override fun init(context: Context?) {
        LogUtils.tag(TAG).i("HelloServiceImpl --> init")
    }
}