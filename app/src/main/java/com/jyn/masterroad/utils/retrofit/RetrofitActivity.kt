package com.jyn.masterroad.utils.retrofit

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.java_kotlin.设计模式.代理模式.RetrofitProxyTest
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityRetrofitBinding

/*
 * 官网 https://square.github.io/retrofit/
 */
@Route(path = RoutePath.Retrofit.path)
class RetrofitActivity : BaseActivity<ActivityRetrofitBinding>(R.layout.activity_retrofit) {
    val retrofitTest by lazy { RetrofitTest() }
    val retrofitProxyTest by lazy { RetrofitProxyTest() }
}
