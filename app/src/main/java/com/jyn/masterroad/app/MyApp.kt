package com.jyn.masterroad.app

import android.app.Application
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ProcessLifecycleOwner
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Utils.MemoryCase
import dagger.hilt.android.HiltAndroidApp

/**
 * 【从入门到实用】android启动优化深入解析
 * https://juejin.cn/post/6955858287040561166
 */
@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate() //        setUncaughtException()
        //第一种可检测activity生命周期的方式
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker()) //原理同下一样
        //第二种可检测activity生命周期的方式
        this.registerActivityLifecycleCallbacks(ActivityLifecycle()) //第三种可检测activity生命周期的方式 - 在BaseActivity中
    }

    /*
     * 全局捕获异常
     * https://mp.weixin.qq.com/s/K8ScqrB9sF9gzxwSAoueCQ
     */
    private fun setUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            LogUtils.tag("MasterRoad").e("线程:" + t.name + " 遇到错误:" + e.message)
            Toast.makeText(this, "程序遇到错误:" + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    LogUtils.tag("MasterRoad").e("程序遇到错误:" + e.message)
                    if (e.message?.startsWith("Unable to start activity") == true) {
                        Toast.makeText(this, "程序遇到错误:" + e.message, Toast.LENGTH_LONG).show()
                        android.os.Process.killProcess(android.os.Process.myPid())
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
        LogUtils.tag("MasterRoad").i("onTerminate")
    }

    /**
     * 低内存
     */
    override fun onLowMemory() {
        super.onLowMemory()
        LogUtils.tag("MasterRoad").i("onLowMemory")
    }

    /**
     * 程序正在清理内存，有7个等级
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        LogUtils.tag("MasterRoad").i("onTrimMemory level:$level")
        MemoryCase.getMemoryCase() //获取内存分配情况
    }

    /**
     * 配置信息改变，如屏幕旋转
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.tag("MasterRoad").i("onConfigurationChanged")
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                LogUtils.tag("MasterRoad").i(" ——> 亮色主题")
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                LogUtils.tag("MasterRoad").i(" ——> 深色主题")
            }
        }
    }
}
