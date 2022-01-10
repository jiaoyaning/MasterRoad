package com.jyn.masterroad.utils.leakCanary2

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Debug
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLeakCanaryBinding
import leakcanary.AppWatcher
import leakcanary.AppWatcher.objectWatcher
import java.lang.Thread.sleep
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import kotlin.concurrent.thread
import leakcanary.internal.*
import leakcanary.*

/*
 * 【带着问题学】关于LeakCanary2.0你应该知道的知识点
 * https://juejin.cn/post/6968084138125590541
 *
 * 微信Android客户端的ANR监控方案
 * https://mp.weixin.qq.com/s/fWoXprt2TFL1tTapt7esYg
 *
 * LeakCanary 2.7 原理分析
 * https://blog.csdn.net/u011033906/article/details/117958603
 */
@Route(path = RoutePath.LeakCanary.path)
class LeakCanaryActivity : BaseActivity<ActivityLeakCanaryBinding>
(R.layout.activity_leak_canary) {

    /**
     * [LeakCanary]
     * 一、添加监听，判断是否内存泄漏
     * 入口 [AppWatcherInstaller.MainProcess]
     *   [AppWatcherInstaller.onCreate]
     *   [AppWatcher.appDefaultWatchers]
     *
     * 监听
     *  1. Activity     [ActivityWatcher]
     *      ActivityLifecycleCallbacks
     *
     *  2. Fragment     [FragmentAndViewModelWatcher] -> [AndroidXFragmentDestroyWatcher]
     *      [FragmentLifecycleCallbacks]
     *
     *  3. ViewMode     [ViewModelClearedWatcher]
     *      反射获取 ViewModelStore 的 mMap
     *
     *  4. RootView     [RootViewWatcher]
     *      通过 [View.addOnAttachStateChangeListener] 的 onViewAttachedToWindow 和onViewDetachedFromWindow 方法回调可做内存泄漏的检查工作:
     *
     *  5. Service      [ServiceWatcher]
     *      Service 通过hook ActivityThread的 H 类和 AMS，当 AMS调用 serviceDoneExecuting 方法时做内存泄漏的检查工作。
     */

    /**
     * 二、分析堆信息，找出引用链
     * [AndroidHeapDumper]
     *
     * [Debug.dumpHprofData] 获取堆信息，Android平台提供
     */

    /**
     * weakReference表示弱引用，其实就是包装了一个强引用，GC的时候，这个被包装的强引用能被释放掉。
     * 如果有ReferenceQueue的话，在释放的时候就把这个Reference放到ReferenceQueue里面，
     * ReferenceQueue里面其实维护的就是Reference的链表而已。
     *
     * 注意：此处为引用而非引用所指向的对象
     */


    /**
     * 缺点：
     * 1. 必须要退出当前Activity才能分析内存，且分析很慢
     * 2. 如果要检测自定义对象需要另写RefWatcher
     *
     */
    private val referenceQueue = ReferenceQueue<Any>() //弱引用被回收时，会把引用塞入到该队列中
    private val weakReference = WeakReference<Activity>(this, referenceQueue)

    companion object {
        const val TAG = "LeakCanary"

        @SuppressLint("StaticFieldLeak")
        var mActivity: Activity? = null
    }

    override fun initData() {
        objectWatcher.addOnObjectRetainedListener {}

        binding.onClick = View.OnClickListener {
            when (it.id) {
                R.id.btn_leak_canary_static -> staticFinish()
                R.id.btn_leak_canary_thread -> threadFinish()
            }
        }
    }


    private fun staticFinish() {
        mActivity = this
        finish()
    }

    private fun threadFinish() {
        object : Thread() {
            override fun run() {
                while (true) {
                    sleep(1000)
                }
            }
        }.start()

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.tag(TAG).i("onDestroy")
        thread() {
            sleep(1000)
            // System.gc() 该gc方法并不能保证每次都执行
            Runtime.getRuntime().gc()
        }.start()

        /**
         * 自定义监听对象
         */
        if (AppWatcher.isInstalled) {
            objectWatcher.expectWeaklyReachable(
                    this,
                    "MyService received Service#onDestroy() callback"
            )
        }
    }

    /**
     * 对象被回收的时候会调用该方法
     */
    protected fun finalize() {
        LogUtils.tag(TAG).i("finalize -> Activity被回收")
    }
}