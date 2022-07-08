package com.jyn.masterroad.kotlin.koin

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.common.Base.BaseActivity
import com.jyn.masterroad.databinding.ActivityKoinBinding
import com.jyn.masterroad.kotlin.koin.data.KoinTestData
import com.jyn.masterroad.kotlin.koin.data.KoinViewModel
import com.jyn.masterroad.kotlin.koin.data.ParameterData
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

/*
 * Kotlin 的依赖注入框架
 * https://insert-koin.io/docs/quickstart/kotlin
 *
 * koin2在android中使用及源码分析
 * https://juejin.cn/post/6844904082994364430
 *
 * kotlin 协程官方文档（5）-异步流（Asynchronous Flow）
 * https://juejin.cn/post/6844904101801639949
 *
 * FragmentFactory 在 Koin 中的应用
 * https://mp.weixin.qq.com/s/1_wTioNnj2K3n0SNSPUHwQ
 */
@Route(path = RoutePath.KotlinKoin.path)
class KoinActivity : BaseActivity<ActivityKoinBinding>
    (R.layout.activity_koin) {

    private val koinTestData: KoinTestData by inject() //懒加载获取
    private val koinViewModel: KoinViewModel by viewModel() //懒加载创建viewmodel
    private val parameterData: ParameterData = get()  //直接获取
    private lateinit var parameterData1: ParameterData
    private val parameterData2: ParameterData by inject(named("more")) {
        parametersOf("修改参1", "修改参2") // 懒加载获取
    }

    override fun initData() {
        parameterData1 = get(named("Special"))  //获取限定符Special注入的类
    }

    override fun initView() {
        binding.click = onClickListener
        binding.koinVM = koinViewModel
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.koin_btn_data_test -> koinTestData.test()
            R.id.koin_btn_named_test -> logNamed()
        }
    }

    private fun logNamed() {
        LogUtils.tag(KoinViewModel.TAG).i(parameterData)
        LogUtils.tag(KoinViewModel.TAG).i(parameterData1)
        LogUtils.tag(KoinViewModel.TAG).i(parameterData2)
    }
}