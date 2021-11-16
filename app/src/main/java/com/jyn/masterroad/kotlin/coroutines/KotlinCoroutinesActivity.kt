package com.jyn.masterroad.kotlin.coroutines

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.Base.BaseScopeActivity
import com.jyn.masterroad.R
import com.jyn.masterroad.databinding.ActivityKotlinCoroutinesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
 * 官方文档
 * https://developer.android.google.cn/kotlin/coroutines
 *
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
 * https://juejin.cn/post/6954250061207306253
 *
 * Android 补给站
 * https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzIzNTc5NDY4Nw==&action=getalbum&album_id=1528448460574752768&scene=173&from_msgid=2247484696&from_itemidx=1&count=3&nolastread=1#wechat_redirect
 *
 * 最全面的Kotlin协程: Coroutine/Channel/Flow 以及实际应用
 * https://juejin.cn/post/6844904037586829320
 *
 * https://lilei.pro/2019/11/17/kotlin-coroutines-01/
 * https://lilei.pro/2019/12/10/kotlin-coroutines-02/
 * https://lilei.pro/2019/12/13/kotlin-coroutines-03/
 * https://lilei.pro/2020/03/16/kotlin-coroutines-04/
 */
@ExperimentalCoroutinesApi
@Route(path = RoutePath.KotlinCoroutines.path)
class KotlinCoroutinesActivity : BaseScopeActivity<ActivityKotlinCoroutinesBinding>
    (R.layout.activity_kotlin_coroutines) {

    private val coroutinesCreate: CoroutinesCreate by lazy { createVM() }

    override fun initView() {
        binding.create = coroutinesCreate
        binding.channel = ChannelTest()
    }

    override fun initData() {
        lifecycleScope.launchWhenCreated {
        }
    }
}