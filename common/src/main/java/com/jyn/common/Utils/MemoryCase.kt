package com.jyn.common.Utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import androidx.core.content.ContextCompat.getSystemService
import com.apkfuns.logutils.LogUtils


/**
 * Android中如何查看内存(上)
 * https://blog.csdn.net/hudashi/article/details/7050897
 */
object MemoryCase {

    /*
     * 应用程序最大分配内存
     */
    fun getMaxMemory(activity: Activity): Int {
        val activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.memoryClass
    }

    /*
     * 应用程序最大可用内存 单位MB
     */
    fun getMaxMemory(): Long {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024
    }

    /*
     * 应用程序已被分配的内存
     */
    fun getTotalMemory(): Long {
        return Runtime.getRuntime().totalMemory() / 1024 / 1024
    }

    /*
     * 应用程序已获得内存中未使用内存
     */
    fun getFreeMemory(): Long {
        return Runtime.getRuntime().freeMemory() / 1024 / 1024
    }

    @JvmStatic
    fun getMemoryCase() {
        LogUtils.tag("main").i("maxMemory : ${getMaxMemory()} MB -- 应用程序最大可用内存")
        LogUtils.tag("main").i("totalMemory : ${getTotalMemory()} MB -- 应用程序已被分配的内存")
        LogUtils.tag("main").i("freeMemory : ${getFreeMemory()} MB -- 应用程序已获得内存中未使用内存")
    }

    @JvmStatic
    fun getMemoryCase2(context: Context) {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager?.getMemoryInfo(memoryInfo)
        LogUtils.tag("main").i("AvailMem : ${memoryInfo.availMem / 1024 / 1024} MB")
        LogUtils.tag("main").i("lowMemory : ${memoryInfo.lowMemory} MB")
        LogUtils.tag("main").i("NativeHeapAllocatedSize : ${Debug.getNativeHeapAllocatedSize() / 1024 / 1024} MB")
    }
}