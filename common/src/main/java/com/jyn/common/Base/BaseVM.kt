package com.jyn.common.Base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.MainScope

/*
 * 从源码看 Jetpack（6）-ViewModel 源码详解
 * https://www.jianshu.com/p/4dc2b3fcac08
 *
 *
 */
@SuppressLint("StaticFieldLeak")
abstract class BaseVM(application: Application) : AndroidViewModel(application), LifecycleObserver {

    companion object {
        const val TAG = "ViewModel"
    }

    var mainScope = MainScope()
    var context: Context = application.baseContext

    // 应用进入后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onAppBackground() {
    }

    // 应用进入前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onAppForeground() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
    }
}