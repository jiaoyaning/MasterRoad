package com.jyn.masterroad.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration
import android.os.Bundle;

import androidx.lifecycle.ProcessLifecycleOwner

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.BuildConfig

import dagger.hilt.android.HiltAndroidApp;

/**
 * Created by jiao on 2020/7/31.
 */
@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        LogUtils.getLogConfig().configShowBorders(false)

        //第一种可检测activity生命周期的方式
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker())

        //第二种可检测activity生命周期的方式
        this.registerActivityLifecycleCallbacks(ActivityLifecycle())
    }

    /**
     * 应用中止
     */
    override fun onTerminate() {
        super.onTerminate()
        LogUtils.i("onTerminate")
    }

    /**
     * 低内存
     */
    override fun onLowMemory() {
        super.onLowMemory()
        LogUtils.i("onLowMemory")
    }

    /**
     * 程序正在清理内存，有7个等级
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        LogUtils.i("onTrimMemory level:$level")
    }

    /**
     * 配置信息改变，如屏幕旋转
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.i("onConfigurationChanged")
    }
}
