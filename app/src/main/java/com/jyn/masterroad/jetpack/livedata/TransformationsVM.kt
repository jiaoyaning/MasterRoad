package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM

/**
 * 怎么使用Transformations
 * https://stackoverflow.com/questions/47610676/how-and-where-to-use-transformations-switchmap
 *
 * https://www.jianshu.com/p/18a3a3dcb8a8
 */
class TransformationsVM(application: Application) : BaseVM(application) {
    companion object {
        const val TAG = "Transformations"
    }

    var liveData: MutableLiveData<Int> = MutableLiveData(0)
    var mapLiveData = Transformations.map(liveData) { "转换成字符串$it" }//可以把int型的liveData转换成别的类型

    fun onClickMapLivedata() {
        liveData.postValue(liveData.value?.plus(1))
    }

    init {
        liveData.observeForever { LogUtils.tag(TAG).i("liveData $it") }
        mapLiveData.observeForever { LogUtils.tag(TAG).i("mapLiveData $it") }
    }
}