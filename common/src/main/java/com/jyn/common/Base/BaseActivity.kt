package com.jyn.masterroad.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<dataBinding : ViewDataBinding> : AppCompatActivity() {

    val binding: dataBinding by lazy {
        DataBindingUtil.setContentView<dataBinding>(this, getLayoutId())
    }

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initView()
    }

    open fun initData() {}
    open fun initView() {}

    /**
     * 获取ViewModel实例
     */
    inline fun <reified T : ViewModel> getVM(): T {
        return ViewModelProvider(this).get(T::class.java)
    }
}