package com.jyn.masterroad.jetpack.hilt;

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
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
    lateinit var hiltInjectData: HiltInjectData

    private val mHitViewModule: HiltViewModel by viewModels()

    override fun initView() {
        binding.hiltInject = hiltInjectData
    }

    override fun initData() {
        mHitViewModule.liveData.observe(this, Observer {
            LogUtils.tag(HiltInjectData.TAG).i("viewModels 如果打印出数据就算成功 --> $it")
        })
    }
}