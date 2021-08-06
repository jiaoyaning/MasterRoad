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
 * Retrofit 结合 Lifecycle, 将 Http 生命周期管理到极致
 * https://mp.weixin.qq.com/s/omCnmMX3XVq-vnKoDnQXTg
 *
 * 三方库源码笔记（7）-Retrofit 源码详解
 * https://mp.weixin.qq.com/s/qWe_tpT65-p1W1zf_AATBg
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
