package com.jyn.masterroad.kotlin.koin.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils

class KoinViewModel(var repo: HelloRepository) : ViewModel() {
    companion object {
        const val TAG = "Koin"
    }

    var num: MutableLiveData<Int> = MutableLiveData(0)

    init {
        LogUtils.tag(TAG).i("KoinTestData ——> 初始化")
    }

    fun add() {
        num.value = num.value?.plus(1)
    }
}