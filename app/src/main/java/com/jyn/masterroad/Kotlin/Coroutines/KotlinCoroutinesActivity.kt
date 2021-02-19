package com.jyn.masterroad.Kotlin.Coroutines

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityKotlinCoroutinesBinding

/*
 * Kotlin协程场景化学习
 * https://mp.weixin.qq.com/s/zQ7fFKp9CCW6h3TVVE6X5g
 */
@Route(path = RoutePath.KotlinCoroutines.path)
class KotlinCoroutinesActivity : BaseActivity<ActivityKotlinCoroutinesBinding>() {

    override fun getLayoutId() = R.layout.activity_kotlin_coroutines

    override fun init() {

    }
}