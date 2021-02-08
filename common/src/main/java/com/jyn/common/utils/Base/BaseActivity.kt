package com.jyn.masterroad.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<dataBinding : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: dataBinding

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<dataBinding>(this, getLayoutId())
        setContentView(binding.root)
        init()
    }

    abstract fun init()
}