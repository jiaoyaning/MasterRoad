package com.jyn.masterroad.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

public abstract class BaseActivity<dataBinding : ViewDataBinding>(var layoutId: Int) : AppCompatActivity() {

    val binding: dataBinding by lazy {
        DataBindingUtil.setContentView<dataBinding>(this, layoutId)
    }

    public fun getProvider(): ViewModelProvider? {
        return ViewModelProvider(this, this.defaultViewModelProviderFactory)
    }
}