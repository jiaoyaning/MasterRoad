package com.jyn.common.Base

import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils

/*
 * 从源码看 Jetpack（6）-ViewModel 源码详解
 * https://www.jianshu.com/p/4dc2b3fcac08
 */
abstract class BaseVM : ViewModel() {
    companion object {
        const val TAG = "ViewModel"
    }

    override fun onCleared() {
        super.onCleared()
        LogUtils.tag(TAG).i("ViewModel ——> onCleared！")
    }
}