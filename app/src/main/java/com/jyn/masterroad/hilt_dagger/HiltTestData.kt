package com.jyn.masterroad.hilt_dagger

import com.apkfuns.logutils.LogUtils
import javax.inject.Inject

/**
 * Created by jiao on 2020/8/10.
 */
class HiltTestData @Inject constructor() {
    var test = "我是一个测试类"

    init {
        LogUtils.tag("main").i("HiltTestData 被初始化……")
    }

    companion object {
        const val staticTest = "hilt_data"
    }

    fun getStaticTest(): String {
        LogUtils.tag("main").i("HiltTestData base:$staticTest")
        return staticTest;
    }
}