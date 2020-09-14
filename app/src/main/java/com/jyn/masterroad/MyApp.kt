package com.jyn.masterroad;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ProcessLifecycleOwner

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils

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

        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker())
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
//                LogUtils.tag("main").i("onActivityPaused:" + activity.localClassName)
            }

            override fun onActivityStarted(activity: Activity) {
//                LogUtils.tag("main").i("onActivityStarted:" + activity.localClassName)
            }

            override fun onActivityDestroyed(activity: Activity) {
//                LogUtils.tag("main").i("onActivityDestroyed:" + activity.localClassName)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//                LogUtils.tag("main").i("onActivitySaveInstanceState:" + activity.localClassName)
            }

            override fun onActivityStopped(activity: Activity) {
//                LogUtils.tag("main").i("onActivityStopped:" + activity.localClassName)
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//                LogUtils.tag("main").i("onActivityCreated:" + activity.localClassName)
            }

            override fun onActivityResumed(activity: Activity) {
//                LogUtils.tag("main").i("onActivityResumed:" + activity.localClassName)
            }

        })
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
