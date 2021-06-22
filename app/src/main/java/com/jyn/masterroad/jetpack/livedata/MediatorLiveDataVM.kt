package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM

/*
 * MediatorLiveData
 * https://blog.csdn.net/hellokai__/article/details/109035698
 *
 */
class MediatorLiveDataVM(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "MediatorLiveData"
    }

    var livedata1: MutableLiveData<Int> = MutableLiveData(0)
    var livedata2: MutableLiveData<Int> = MutableLiveData(10)
    var liveDataMerger: MediatorLiveData<Int> = MediatorLiveData()

    init {
        liveDataMerger.addSource(livedata1) {
            liveDataMerger.postValue(it)
            LogUtils.tag(TAG).i("livedata1 $it")
        }
        liveDataMerger.addSource(livedata2) {
            liveDataMerger.postValue(it)
            LogUtils.tag(TAG).i("livedata2 $it")
        }
        liveDataMerger.observeForever {
            LogUtils.tag(TAG).i("liveDataMerger $it")
        }
    }

    fun onClickLiveData1() {
        livedata1.postValue(livedata1.value?.plus(1))
    }

    fun onClickLiveData2() {
        livedata2.postValue(livedata2.value?.minus(1))
    }
}