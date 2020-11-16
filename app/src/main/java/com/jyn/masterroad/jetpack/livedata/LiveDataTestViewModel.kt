package com.jyn.masterroad.jetpack.livedata

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils
import kotlinx.android.synthetic.main.activity_live_data.*

class LiveDataTestViewModel : ViewModel() {

    var numString: MutableLiveData<String> = MutableLiveData("1111")

    var num: MutableLiveData<Int> = MutableLiveData(0)

    var numObservable = ObservableInt(0)

    init {
        numObservable.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                LogUtils.tag("main").i("我改变了$propertyId")
            }
        })
    }

    /**
     * 点击方法设置之，直接在xml中绑定
     */
    fun add(v: View) {
        num.value = num.value?.plus(1);
//        num.set(num.get().plus(1))
        numString.value = "add"
    }

    fun subtract() {
        num.value = num.value?.minus(1)
//        num.set(num.get().minus(1))
    }
}