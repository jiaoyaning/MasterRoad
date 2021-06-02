package com.jyn.masterroad.utils.okhttp_retrofit

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityOkhttpBinding

/*
 * 优雅的网络请求：Retrofit+Kotlin协程
 * https://mp.weixin.qq.com/s/LsYw4bg5q7jtMP4M0yWmzQ
 */
@Route(path = RoutePath.OkHttp.path)
class OkhttpActivity : BaseActivity<ActivityOkhttpBinding>
(R.layout.activity_okhttp) {

}