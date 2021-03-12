package com.jyn.masterroad.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.apkfuns.logutils.LogUtils

class ActivityLifecycle : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//        LogUtils.tag("MasterRoad").i("onActivityCreated:" + activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {
//        LogUtils.tag("MasterRoad").i("onActivityStarted:" + activity.localClassName)
    }

    override fun onActivityResumed(activity: Activity) {
//        LogUtils.tag("MasterRoad").i("onActivityResumed:" + activity.localClassName)
    }

    override fun onActivityPaused(activity: Activity) {
//        LogUtils.tag("MasterRoad").i("onActivityPaused:" + activity.localClassName)
    }

    override fun onActivityStopped(activity: Activity) {
//        LogUtils.tag("MasterRoad").i("onActivityStopped:" + activity.localClassName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        LogUtils.tag("MasterRoad").i("onActivitySaveInstanceState:" + activity.localClassName)
    }

    override fun onActivityDestroyed(activity: Activity) {
//        LogUtils.tag("MasterRoad").i("onActivityDestroyed:" + activity.localClassName)
    }
}