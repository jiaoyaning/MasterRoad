package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jyn.common.Base.BaseVM

/**
 * MediatorLiveData
 * https://blog.csdn.net/hellokai__/article/details/109035698
 */
class MediatorLiveDataVM(application: Application) : BaseVM(application) {
    var livedata1: MutableLiveData<Int> = MutableLiveData(0)
    var livedata2: MutableLiveData<Int> = MutableLiveData(10)

    var liveDataMerger: MediatorLiveData<Int> = MediatorLiveData()

    init {
        liveDataMerger.addSource(livedata1) { liveDataMerger.postValue(it) }
        liveDataMerger.addSource(livedata2) { liveDataMerger.postValue(it) }
    }

    fun onClickLiveData1() {
        livedata1.postValue(livedata1.value?.plus(1))
    }

    fun onClickLiveData2() {
        livedata2.postValue(livedata1.value?.minus(1))
    }
}