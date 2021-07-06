package com.jyn.masterroad.utils.leakCanary2

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLeakCanaryBinding

/*
 * 【带着问题学】关于LeakCanary2.0你应该知道的知识点
 * https://juejin.cn/post/6968084138125590541
 */
@Route(path = RoutePath.LeakCanary.path)
class LeakCanaryActivity : BaseActivity<ActivityLeakCanaryBinding>(R.layout.activity_leak_canary) {

}