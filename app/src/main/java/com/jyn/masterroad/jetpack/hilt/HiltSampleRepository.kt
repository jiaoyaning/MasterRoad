package com.jyn.masterroad.jetpack.hilt

import com.apkfuns.logutils.LogUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HiltSampleRepository @Inject constructor() {
    companion object {
        private const val TAG = "Hilt"
    }

    fun update(data: String) {
        LogUtils.tag(TAG).i("如果打印出数据就算成功 --> $data")
    }
}