package com.jyn.masterroad.jetpack.startup

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.BuildConfig

/*
 * https://guolin.blog.csdn.net/article/details/108026357
 * create()方法初始化
 * dependencies()初始化依赖
 *
 * 第三方startup框架
 * https://blog.csdn.net/fan757709373/article/details/108526626
 * implementation 'com.rousetime.android:android-startup:latest release'
 */
class LitePalInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(context as Application)
        LogUtils.getLogConfig().configShowBorders(false)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
//        return listOf(LitePalInitializer::class.java) //可实现多级 依赖的组件
    }
}