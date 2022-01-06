package com.jyn.masterroad.utils.arouter

import android.content.Context
import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PathReplaceService
import com.apkfuns.logutils.LogUtils

/**
 * 重新定以URL的跳转
 */
@Route(path = "/app/pathReplaceService")
class PathReplaceServiceImpl : PathReplaceService {

    companion object {
        const val TAG = "ARouter"
    }

    override fun init(context: Context?) {
        LogUtils.tag(TAG).i("PathReplaceServiceImpl --> init")
    }

    override fun forString(path: String): String {
        if (path == "/app/main") {
            return "/app/handler"
        }
        LogUtils.tag(TAG).i("PathReplaceServiceImpl --> path:$path")
        return path
    }

    override fun forUri(uri: Uri): Uri {
        return uri
    }
}