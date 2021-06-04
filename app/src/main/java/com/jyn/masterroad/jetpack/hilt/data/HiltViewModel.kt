package com.jyn.masterroad.jetpack.hilt.data

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.jyn.common.Base.BaseVM

/**
 * Created by jiaoyaning on 2021/5/14.
 */
class HiltViewModel @ViewModelInject constructor(application: Application) : BaseVM(application) {
    var num: MutableLiveData<Int> = MutableLiveData(0)

    fun add() {
        num.value = num.value?.plus(1)
    }

    fun subtract() {
        num.value = num.value?.minus(1)
    }
}