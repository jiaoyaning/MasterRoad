package com.jyn.masterroad.jetpack.hilt.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.kotlin.koin.data.KoinViewModel

/**
 * Created by jiaoyaning on 2021/5/14.
 */
class HiltViewModel @ViewModelInject constructor() : ViewModel() {
    var num: MutableLiveData<Int> = MutableLiveData(0)

    fun add() {
        num.value = num.value?.plus(1)
    }

    fun subtract() {
        num.value = num.value?.minus(1)
    }
}