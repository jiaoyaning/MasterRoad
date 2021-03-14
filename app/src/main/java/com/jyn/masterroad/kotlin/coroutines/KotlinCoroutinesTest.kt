package com.jyn.masterroad.kotlin.coroutines

import android.view.View
import com.apkfuns.logutils.LogUtils

class KotlinCoroutinesTest {
    fun simpleTest(v: View?) {
        LogUtils.tag("Coroutines").i("包名不能大写啊，不能大写")
    }
}