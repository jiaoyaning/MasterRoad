package com.jyn.common.Base

import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseScopeActivity<dataBinding : ViewDataBinding>(id: Int) :
    BaseActivity<dataBinding>(id), CoroutineScope by MainScope() {

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}