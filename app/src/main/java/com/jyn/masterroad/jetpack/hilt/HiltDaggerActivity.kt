package com.jyn.masterroad.jetpack.hilt;

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityHiltDaggerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
 * 官网
 * https://developer.android.google.cn/training/dependency-injection?hl=zh_cn
 *
 * Jetpack 成员 Hilt 实践（一）启程过坑记
 * https://mp.weixin.qq.com/s/6yqsc6P4aUn5alvzf9PPVg
 */
@AndroidEntryPoint
@Route(path = RoutePath.Hilt.path)
class HiltDaggerActivity : BaseActivity<ActivityHiltDaggerBinding>
    (R.layout.activity_hilt_dagger) {

    @Inject
    lateinit var hiltInjectData: HiltInjectData

    override fun initView() {
        binding.hiltInject = hiltInjectData
    }
}