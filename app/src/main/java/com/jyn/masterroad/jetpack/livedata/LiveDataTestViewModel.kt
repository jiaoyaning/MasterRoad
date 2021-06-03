package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM

/**
 * https://github.com/android/architecture-components-samples/tree/main/LiveDataSample
 */
class LiveDataTestViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "LiveData"
    }

    var numString: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                numString = MutableLiveData("0")
            }
            return field
        }

    var num: MutableLiveData<Int> = MutableLiveData(10)

    val liveData = liveData {
        emit(" i am a ViewModelInject")
    }

    var entries: SingleLiveEvent<List<String>> =
        SingleLiveEvent(mutableListOf("测试1", "测试2", "测试3", "测试4"))
    var select: SingleLiveEvent<String> = SingleLiveEvent("测试3")

    /*
     * ObservableField只有在数据发生改变时UI才会收到通知，而LiveData不同，
     * 只要你postValue或者setValue，UI都会收到通知，不管数据有无变化
     *
     * LiveData能感知Activity的生命周期，在Activity不活动的时候不会触发，例如一个Activity不在任务栈顶部
     *
     * ps:ObservableField无法感知生命周期，如果页面已销毁，可能会导致应用崩溃
     */
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
        num.value = num.value?.plus(1)
        numString?.value = num.value.toString()
    }

    fun subtract() {
        num.value = num.value?.minus(1)
        numString?.value = num.value.toString()
    }
}