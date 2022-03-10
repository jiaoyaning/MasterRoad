package com.jyn.masterroad.jetpack.hilt.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jyn.common.Base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by jiaoyaning on 2021/5/14.
 */
@HiltViewModel
class HiltViewModelTest @Inject constructor(application: Application) : BaseVM(application) {
    var num: MutableLiveData<Int> = MutableLiveData(0)

    fun add() {
        num.value = num.value?.plus(1)
    }

    fun subtract() {
        num.value = num.value?.minus(1)
    }
}