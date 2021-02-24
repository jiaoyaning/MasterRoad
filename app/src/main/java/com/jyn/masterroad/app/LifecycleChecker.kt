package com.jyn.masterroad.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.apkfuns.logutils.LogUtils

/*
 * Activity是如何实现LifecycleOwner的？
 * https://juejin.cn/post/6925620141170524167
 */
internal class LifecycleChecker : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackground() {
        // 应用进入后台
        LogUtils.tag("main").i("LifecycleChecker onAppBackground ON_STOP 应用进入后台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForeground() {
        // 应用进入前台
        LogUtils.tag("main").i("LifecycleChecker onAppForeground ON_START 应用进入前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogUtils.tag("main").i("LifecycleChecker onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        LogUtils.tag("main").i("LifecycleChecker onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        LogUtils.tag("main").i("LifecycleChecker onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        LogUtils.tag("main").i("LifecycleChecker onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        LogUtils.tag("main").i("LifecycleChecker onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtils.tag("main").i("LifecycleChecker onDestroy")
    }
}