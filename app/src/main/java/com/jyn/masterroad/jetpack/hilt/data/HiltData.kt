package com.jyn.masterroad.jetpack.hilt.data

import com.apkfuns.logutils.LogUtils
import javax.inject.Inject

/**
 * 一个最基本的Inject Data
 */
class HiltData @Inject constructor() {
    companion object {
        const val TAG = "Hilt"
    }

    var default = "我是一个HiltInject测试类中的一个参数"

    init {
        LogUtils.tag(TAG).i("HiltInjectData 被初始化……")
    }

    fun print() {
        LogUtils.tag(TAG).i("如果打印出数据就算成功 --> $default")
    }
}