package com.jyn.masterroad.jetpack.hilt;

import androidx.activity.viewModels
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
 *
 * Jetpack 成员 Hilt 结合 App Startup（二）进阶篇
 * https://mp.weixin.qq.com/s/-B7pCNFncGaBW3AJEy8zWA
 *
 * 全方面分析 Hilt 和 Koin 性能
 * https://mp.weixin.qq.com/s/PsiCIOiV8FWVQ9HF8EvJ8w
 */
@AndroidEntryPoint
@Route(path = RoutePath.Hilt.path)
class HiltDaggerActivity : BaseActivity<ActivityHiltDaggerBinding>
    (R.layout.activity_hilt_dagger) {

    @Inject
    lateinit var hiltData: HiltData

    private val hitViewModule: HiltViewModel by viewModels()
    private val hitViewModuleSaved: HiltViewModelSaved by viewModels()

    override fun initView() {
        binding.hiltInject = hiltData
        binding.hiltViewModel = hitViewModule
        hitViewModuleSaved.insert()
    }
}