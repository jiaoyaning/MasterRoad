package com.jyn.masterroad.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

public abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val binding: T by lazy {
        DataBindingUtil.setContentView<T>(this, getLayoutId())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    public fun getProvider(): ViewModelProvider? {
        return ViewModelProvider(this, this.defaultViewModelProviderFactory)
    }
}