package com.jyn.masterroad.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

public abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public fun getActivityViewModelProvider(activity: AppCompatActivity): ViewModelProvider? {
        return ViewModelProvider(activity, activity.defaultViewModelProviderFactory)
    }
}