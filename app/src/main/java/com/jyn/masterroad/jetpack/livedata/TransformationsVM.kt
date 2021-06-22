package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM

/*
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

    /**
     * map可以转换类型
     * 🌰：把int类型的LiveData 转换为string类型
     */
    var mapLiveData: LiveData<String> = Transformations.map(liveData) {
        "转换成字符串$it"
    }

    var switchMapLiveData = Transformations.switchMap(liveData) {
        MutableLiveData("全新的LiveData$it")
    }

    fun onClickMapLivedata() {
        liveData.postValue(liveData.value?.plus(1))
    }

    fun onClickSwitchMapLivedata() {
        liveData.postValue(liveData.value?.plus(1))
    }

    init {
        liveData.observeForever { LogUtils.tag(TAG).i("liveData $it") }
        mapLiveData.observeForever { LogUtils.tag(TAG).i("mapLiveData $it") }
    }
}