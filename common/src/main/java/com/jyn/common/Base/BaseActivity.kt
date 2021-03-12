package com.jyn.masterroad.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<dataBinding : ViewDataBinding> : AppCompatActivity() {

    val binding: dataBinding by lazy {
        DataBindingUtil.setContentView<dataBinding>(this, getLayoutId())
    }

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    open fun initView() {}
}