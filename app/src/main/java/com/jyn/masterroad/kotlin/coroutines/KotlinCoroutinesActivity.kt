package com.jyn.masterroad.kotlin.coroutines

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityKotlinCoroutinesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
 * Kotlin 协程 系列教程
 * http://mp.weixin.qq.com/mp/homepage?__biz=MzIzMTYzOTYzNA==&hid=4&sn=eb02d1dc6f5d92096f214688c6f87196&scene=18#wechat_redirect
 *
 * 硬核万字解读——Kotlin协程原理解析 TODO
 * https://mp.weixin.qq.com/s/N9BiufCWTRuoh6J-QERlWQ
 *
 * 超长文，带你全面了解Kotlin的协程 TODO
 * https://mp.weixin.qq.com/s/bUK8UKg9ZXykhvbiASpyVg
 *
 * 使用协程，让网络世界更加美好
 * https://mp.weixin.qq.com/s/84fSUYQt3T_Fa5B5s5ihvA
 *
 * 史上最详Android版kotlin协程入门进阶实战  TODO
 * https://juejin.cn/post/6953287252373930021
 */
@ExperimentalCoroutinesApi
@Route(path = RoutePath.KotlinCoroutines.path)
class KotlinCoroutinesActivity : BaseActivity<ActivityKotlinCoroutinesBinding>
    (R.layout.activity_kotlin_coroutines) {

    private val kotlinCoroutinesCreate: KotlinCoroutinesCreate by lazy { createVM() }

    private val kotlinCoroutinesTest: KotlinCoroutinesTest by lazy {
        createVM<KotlinCoroutinesTest>().apply {
            lifecycleScope = this@KotlinCoroutinesActivity.lifecycleScope
        }
    }

    override fun initView() {
        binding.coroutines = kotlinCoroutinesTest
        binding.create = kotlinCoroutinesCreate
    }
}