package com.jyn.masterroad.utils.leakCanary2

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLeakCanaryBinding
import leakcanary.AppWatcher

/*
 * 【带着问题学】关于LeakCanary2.0你应该知道的知识点
 * https://juejin.cn/post/6968084138125590541
 *
 * 微信Android客户端的ANR监控方案
 * https://mp.weixin.qq.com/s/fWoXprt2TFL1tTapt7esYg
 *
 * 被问到：如何检测线上内存泄漏，通过 LeakCanary 探究！
 * https://mp.weixin.qq.com/s/F3sAvCsO8sXG5oOUrQG_6g
 *
 * 天天用LeakCanary，不了解原理能忍？
 * https://mp.weixin.qq.com/s/idjFaJsLpVLw52RSYHA_Vg
 *
 * LeakCanary 新版 2.x ，你应该知道的知识点
 * https://mp.weixin.qq.com/s/bFQEG3sYlwQdct5KNouBIA
 */
@Route(path = RoutePath.LeakCanary.path)
class LeakCanaryActivity : BaseActivity<ActivityLeakCanaryBinding>
(R.layout.activity_leak_canary) {
    /**
     * [leakcanary.internal.AppWatcherInstaller]
     * [AppWatcher.manualInstall]
     *
     * Activity     [leakcanary.ActivityWatcher]
     * Fragment     [leakcanary.FragmentAndViewModelWatcher]
     * RootView     [leakcanary.RootViewWatcher]
     * Service      [leakcanary.ServiceWatcher]
     */
    override fun initData() {

    }
}