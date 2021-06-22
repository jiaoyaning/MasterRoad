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

    private var liveData1: MutableLiveData<Int> = MutableLiveData(0)
    private var liveData2: MutableLiveData<Int> = MutableLiveData(0)

    /**
     * map可以转换类型
     * 🌰：把int类型的LiveData 转换为string类型
     */
    var mapLiveData: LiveData<String> = Transformations.map(liveData1) {
        "转换成字符串$it"
    }

    var switchMapLiveData = Transformations.switchMap(liveData2) {
        MutableLiveData("全新的LiveData$it")
    }

    fun onClickMapLivedata() {
        liveData1.postValue(liveData1.value?.plus(1))
    }

    fun onClickSwitchMapLivedata() {
        liveData2.postValue(liveData2.value?.plus(1))
    }

    init {
        liveData1.observeForever { LogUtils.tag(TAG).i("liveData $it") }
        liveData2.observeForever { LogUtils.tag(TAG).i("liveData $it") }
        mapLiveData.observeForever { LogUtils.tag(TAG).i("mapLiveData $it") }
        switchMapLiveData.observeForever { LogUtils.tag(TAG).i("switchMapLiveData $it") }
    }
}