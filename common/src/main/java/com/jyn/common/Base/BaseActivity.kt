package com.jyn.masterroad.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
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
        init()
    }

    abstract fun init()
}