package com.jyn.masterroad;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by jiao on 2020/8/26.
 */
class LifecycleChecker implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackground() {
        // 应用进入后台
        LogUtils.tag("main").i("LifecycleChecker onAppBackground ON_STOP 应用进入后台");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForeground() {
        // 应用进入前台
        LogUtils.tag("main").i("LifecycleChecker onAppForeground ON_START 应用进入前台");
    }
}
