package com.jyn.masterroad.app;

import android.app.Application;
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.widget.Toast

import androidx.lifecycle.ProcessLifecycleOwner

import com.apkfuns.logutils.LogUtils

import dagger.hilt.android.HiltAndroidApp;

/**
 * Created by jiao on 2020/7/31.
 */
@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setUncaughtException()

        //第一种可检测activity生命周期的方式
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker())

        //第二种可检测activity生命周期的方式
        this.registerActivityLifecycleCallbacks(ActivityLifecycle())
    }

    /**
     * 全局捕获异常
     * https://mp.weixin.qq.com/s/K8ScqrB9sF9gzxwSAoueCQ
     */
    private fun setUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            LogUtils.tag("main").e("程序遇到错误:" + e.message)
            Toast.makeText(this, "程序遇到错误:" + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    e.printStackTrace();
                    LogUtils.tag("main").e("程序遇到错误:" + e.message)
                    if (e.message?.startsWith("Unable to start activity") == true) {
                        Toast.makeText(this, "程序遇到错误:" + e.message, Toast.LENGTH_LONG).show()
                        // TODO 来自 Activity 生命周期崩溃，杀死进程
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break
                    }
                }
            }
        }
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
