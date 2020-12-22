package com.jyn.masterroad.app;

import android.app.Application
import com.apkfuns.logutils.LogUtils
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by jiao on 2020/7/31.
 */
@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.getLogConfig().configShowBorders(false)
    }
}
