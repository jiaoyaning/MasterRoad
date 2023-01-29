package com.jyn.masterroad.app

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.apkfuns.logutils.LogUtils

class LifecycleChecker2 : DefaultLifecycleObserver, LifecycleEventObserver {
    override fun onCreate(owner: LifecycleOwner) {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker2 -> onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker2 -> onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker2 -> onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker2 -> onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker2 -> onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
//        LogUtils.tag("MasterRoad").i("LifecycleChecker2 -> onDestroy")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        LogUtils.tag("MasterRoad").i("onStateChanged -> ${event.name}")
    }
}