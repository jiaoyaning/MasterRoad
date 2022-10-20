package com.jyn.common.Base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
 * 封装DataBinding-新写法
 * https://mp.weixin.qq.com/s/2os4z9lhuahYPbB-UtPmcg
 *
 * Google挖坑后人埋-ViewBinding(下)
 * https://mp.weixin.qq.com/s/aUr3EV-Vizc-fnpBRYg8Hg
 */
abstract class BaseActivity<dataBinding : ViewDataBinding>(var id: Int = 0) : AppCompatActivity() {

    val binding: dataBinding by lazy {
        DataBindingUtil.setContentView<dataBinding>(this, id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this //不添加这句，视图可能会不刷新
        initData()
        initView()
    }

    open fun initData() {}
    open fun initView() {}

    open fun bindVM(viewModelId: Int, viewModel: ViewModel) {
        binding.setVariable(viewModelId, viewModel)
    }

    /*
     * 获取ViewModel实例
     *
     * reified : 由于java泛型是伪泛型,为了兼容java1.5以前的版本,java运行时,会泛型擦除 会擦除为泛型上界,
     * 如果没有泛型上界会擦除为Object,所以jvm在程序运行时是不知道泛型的真实类型,
     * reified 能保证运行时依然能拿到泛型的具体类型.(当前只限制支持内联函数可用)
     */
    inline fun <reified T : ViewModel> createVM(): T {
        return ViewModelProvider(this)[T::class.java]
    }
}