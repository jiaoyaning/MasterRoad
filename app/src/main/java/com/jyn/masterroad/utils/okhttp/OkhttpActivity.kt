package com.jyn.masterroad.utils.okhttp

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityOkhttpBinding

/*
 * OkHttp官方文档
 * https://square.github.io/okhttp/
 *
 * 一步步封装实现自己的网络请求框架 3.0
 * https://mp.weixin.qq.com/s/QHHdGCqlMqujPRopGWHImA
 *
 * OkOne-如何给okhttp的请求设置优先级
 * https://blog.csdn.net/dehang0/article/details/113051267
 */
@Route(path = RoutePath.OkHttp.path)
class OkhttpActivity : BaseActivity<ActivityOkhttpBinding>(R.layout.activity_okhttp) {

    val okhttpViewModel: OkhttpViewModel by viewModels()

    override fun initView() {
        binding.okhttpViewModel = okhttpViewModel
    }
}