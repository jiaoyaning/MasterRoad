package com.jyn.masterroad.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.apkfuns.logutils.LogUtils

/**
 * Created by jiao on 2020/9/16.
 */
class ActivityLifecycle : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        LogUtils.tag("main").i("onActivityCreated:" + activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.tag("main").i("onActivityStarted:" + activity.localClassName)
    }

    override fun onActivityResumed(activity: Activity) {
        LogUtils.tag("main").i("onActivityResumed:" + activity.localClassName)
    }

    override fun onActivityPaused(activity: Activity) {
        LogUtils.tag("main").i("onActivityPaused:" + activity.localClassName)
    }

    override fun onActivityStopped(activity: Activity) {
        LogUtils.tag("main").i("onActivityStopped:" + activity.localClassName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        LogUtils.tag("main").i("onActivitySaveInstanceState:" + activity.localClassName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        LogUtils.tag("main").i("onActivityDestroyed:" + activity.localClassName)
    }
}