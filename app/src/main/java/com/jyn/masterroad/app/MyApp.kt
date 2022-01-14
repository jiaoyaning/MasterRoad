package com.jyn.masterroad.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ProcessLifecycleOwner
import com.apkfuns.logutils.LogUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.jyn.common.Utils.MemoryCase
import com.jyn.common.Utils.TimeUtils
//import com.kwai.koom.base.MonitorLog
//import com.kwai.koom.base.MonitorManager
//import com.kwai.koom.javaoom.monitor.OOMHprofUploader
//import com.kwai.koom.javaoom.monitor.OOMMonitorConfig
//import com.kwai.koom.javaoom.monitor.OOMReportUploader
import dagger.hilt.android.HiltAndroidApp
import java.io.File

/*
 * 【从入门到实用】android启动优化深入解析
 * https://juejin.cn/post/6955858287040561166
 *
 * 原创|教你一分钟之内定位App冷启动卡顿
 * https://mp.weixin.qq.com/s/L9KWV0IviifLnGMPSpUmOA
 *
 * Android Lint代码检查实践
 * https://juejin.cn/post/6861562664582119432
 *
 * Android正确的保活方案，不要掉进保活需求死循环陷进
 * https://juejin.cn/post/7003992225575075876
 *
 * Android 控制 ContentProvider的创建
 * https://juejin.cn/post/7007338307075964942
 *
 * 反思｜Android 输入系统 & ANR机制的设计与实现
 * https://juejin.cn/post/6864555867023343623
 *
 * 抖音BoostMultiDex优化实践
 * https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI1MzYzMjE0MQ==&action=getalbum&album_id=1590431672955617284&scene=173&from_msgid=2247485522&from_itemidx=1&count=3&nolastread=1#wechat_redirect
 *
 * 面试官：今日头条启动很快，你觉得可能是做了哪些优化？
 * https://juejin.cn/post/6844903958113157128
 */
@HiltAndroidApp
class MyApp : Application() {

    /**
     * APP最早执行的方法
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        TimeUtils.beginTimeCalculate(TimeUtils.COLD_START);
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        // setUncaughtException()

        // 第一种可检测activity生命周期的方式
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleChecker()) //原理同下一样
        // 第二种可检测activity生命周期的方式
        this.registerActivityLifecycleCallbacks(ActivityLifecycle()) //第三种可检测activity生命周期的方式 - 在BaseActivity中

        val debuggerConnected = Debug.isDebuggerConnected() //判断apk是否运行在调试状态
        LogUtils.tag("MasterRoad").e("是否在调试状态:$debuggerConnected")

//        initMonitor()
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

//    fun initMonitor(){
//        val config = OOMMonitorConfig.Builder()
//                .setThreadThreshold(50) //50 only for test! Please use default value!
//                .setFdThreshold(300) // 300 only for test! Please use default value!
//                .setHeapThreshold(0.9f) // 0.9f for test! Please use default value!
//                .setVssSizeThreshold(1_000_000) // 1_000_000 for test! Please use default value!
//                .setMaxOverThresholdCount(1) // 1 for test! Please use default value!
//                .setAnalysisMaxTimesPerVersion(3) // Consider use default value！
//                .setAnalysisPeriodPerVersion(15 * 24 * 60 * 60 * 1000) // Consider use default value！
//                .setLoopInterval(5_000) // 5_000 for test! Please use default value!
//                .setEnableHprofDumpAnalysis(true)
//                .setHprofUploader(object: OOMHprofUploader {
//                    override fun upload(file: File, type: OOMHprofUploader.HprofType) {
//                        MonitorLog.e("OOMMonitor", "todo, upload hprof ${file.name} if necessary")
//                    }
//                })
//                .setReportUploader(object: OOMReportUploader {
//                    override fun upload(file: File, content: String) {
//                        MonitorLog.i("OOMMonitor", content)
//                        MonitorLog.e("OOMMonitor", "todo, upload report ${file.name} if necessary")
//                    }
//                })
//                .build()
//
//        MonitorManager.addMonitorConfig(config)
//    }
}
