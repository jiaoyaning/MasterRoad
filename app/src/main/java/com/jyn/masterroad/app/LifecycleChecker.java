package com.jyn.masterroad.app;

import android.util.Log;

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

//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    public void onCreate() {
//        LogUtils.tag("main").i("LifecycleChecker onCreate");
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void onStart() {
//        LogUtils.tag("main").i("LifecycleChecker onStart");
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    public void onResume() {
//        LogUtils.tag("main").i("LifecycleChecker onResume");
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    public void onPause() {
//        LogUtils.tag("main").i("LifecycleChecker onPause");
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    public void onStop() {
//        LogUtils.tag("main").i("LifecycleChecker onStop");
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    public void onDestroy() {
//        LogUtils.tag("main").i("LifecycleChecker onDestroy");
//    }
}
