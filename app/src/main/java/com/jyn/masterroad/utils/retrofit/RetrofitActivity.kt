package com.jyn.masterroad.utils.retrofit

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.Base.BaseScopeActivity
import com.jyn.masterroad.R
import com.jyn.masterroad.databinding.ActivityRetrofitBinding
import com.jyn.masterroad.utils.retrofit.proxy.RetrofitProxyTest

/*
 * 官网 https://square.github.io/retrofit/
 *
 * 三方库源码笔记（7）-Retrofit 源码详解
 * https://mp.weixin.qq.com/s/qWe_tpT65-p1W1zf_AATBg
 *
 * 入木三分：从设计者角度看Retrofit原理
 * https://mp.weixin.qq.com/s/7GuX3ooc9Bo1P9FJyGkZ8g
 *
 * 使用协程，让网络世界更加美好
 * https://mp.weixin.qq.com/s/84fSUYQt3T_Fa5B5s5ihvA
 */
@Route(path = RoutePath.Retrofit.path)
class RetrofitActivity : BaseScopeActivity<ActivityRetrofitBinding>(R.layout.activity_retrofit) {

    private val retrofitViewModel: RetrofitViewModel by viewModels()
    private val retrofitProxyTest by lazy { RetrofitProxyTest() }

    override fun initData() {
        binding.retrofitViewModel = retrofitViewModel
        binding.retrofitProxyTest = retrofitProxyTest
    }
}
