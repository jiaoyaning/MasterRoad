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
 */
@Route(path = RoutePath.Retrofit.path)
class RetrofitActivity : BaseScopeActivity<ActivityRetrofitBinding>(R.layout.activity_retrofit) {

    private val retrofitViewModel:RetrofitViewModel by viewModels()
    private val retrofitProxyTest by lazy { RetrofitProxyTest() }

    override fun initData() {
        binding.retrofitViewModel = retrofitViewModel
        binding.retrofitProxyTest = retrofitProxyTest
    }
}
