package com.jyn.masterroad.utils.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.apkfuns.logutils.LogUtils

/**
 * 拦截器可以定义优先级，如果有多个拦截器，会依次执行拦截器。
 */
@Interceptor(priority = 8, name = "拦截器测试")
class InterceptorServiceImpl : IInterceptor {
    companion object {
        const val TAG = "ARouter"
    }

    override fun init(context: Context?) {
        LogUtils.tag(TAG).i("InterceptorServiceImpl --> init")
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback?) {
        val path = postcard.path
        if (path == "/app/main") {
            callback?.onInterrupt(Throwable("主页没有路由"))
        } else {
            callback?.onContinue(postcard)
        }
    }
}