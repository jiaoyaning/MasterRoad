package com.jyn.masterroad.jetpack.livedata

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils
import androidx.lifecycle.MutableLiveData

class LiveDataTestViewModel : ViewModel() {

    var numString: MutableLiveData<String>? = null //千万不能直接初始化一个默认值，否则get时就会永远获取默认值
        get() {
            if (field == null) {
                numString = MutableLiveData("0")
            }
            return field
        }

    var num: MutableLiveData<Int> = MutableLiveData(0)
        set(value) {
            numString?.value = value.toString()
            field = value
        }

    private var numObservable = ObservableInt(0)

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
        numString?.value = num.value.toString()
    }

    fun subtract() {
        num.value = num.value?.minus(1)
        numString?.value = num.value.toString()
    }
}