package com.jyn.masterroad.kotlin.koin

import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityKoinBinding
import com.jyn.masterroad.kotlin.koin.data.KoinTestData
import com.jyn.masterroad.kotlin.koin.data.KoinViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
 * Kotlin 的依赖注入框架
 * https://insert-koin.io/docs/quickstart/kotlin
 */
@Route(path = RoutePath.KotlinKoin.path)
class KoinActivity : BaseActivity<ActivityKoinBinding>
    (R.layout.activity_koin) {

    private val koinTestData: KoinTestData by inject()
    private val koinViewModel: KoinViewModel by viewModel()

    override fun initView() {
        binding.click = onClickListener
        binding.koinVM = koinViewModel

        koinViewModel.num.observe(this, Observer {
            LogUtils.tag(KoinViewModel.TAG).i("koinViewModel num ——> $it")
        })
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.koin_btn_data_test -> koinTestData.test()
        }
    }
}