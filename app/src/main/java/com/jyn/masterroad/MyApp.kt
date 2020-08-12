package com.jyn.masterroad;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
}
