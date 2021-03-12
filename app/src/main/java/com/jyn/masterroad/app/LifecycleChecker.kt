package com.jyn.masterroad.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.apkfuns.logutils.LogUtils

/*
 * Activity是如何实现LifecycleOwner的？
 * https://juejin.cn/post/6925620141170524167
 */
class LifecycleChecker : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackground() {
        // 应用进入后台
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onAppBackground ON_STOP 应用进入后台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForeground() {
        // 应用进入前台
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onAppForeground ON_START 应用进入前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker onDestroy")
    }
}