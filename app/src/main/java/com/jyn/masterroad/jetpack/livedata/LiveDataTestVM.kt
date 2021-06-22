package com.jyn.masterroad.jetpack.livedata

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

/*
 * https://github.com/android/architecture-components-samples/tree/main/LiveDataSample
 *
 * 面试官：你了解 LiveData 的 postValue 吗？
 * https://mp.weixin.qq.com/s/yMO1oDSUAGmPJ4w4hqSWPw
 */
class LiveDataTestVM(application: Application) : BaseVM(application) {

    companion object {
        const val TAG = "LiveData"
    }

    /**
     * ObservableField只有在数据发生改变时UI才会收到通知，而LiveData不同，
     * 只要你postValue或者setValue，UI都会收到通知，不管数据有无变化
     *
     * LiveData能感知Activity的生命周期，在Activity不活动的时候不会触发(位于start状态之后)，例如一个Activity不在任务栈顶部
     *
     * ps:ObservableField无法感知生命周期，如果页面已销毁，可能会导致应用崩溃
     */
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

    private var postValueNum = object : MutableLiveData<Int>(0) {
        override fun setValue(value: Int?) {
            super.setValue(value)
            LogUtils.tag(TAG).i("setValue : $value")
        }
    }
    //region Observable
    private var numObservable = ObservableInt(0)

    init {
        numObservable.addOnPropertyChangedCallback(object :
            androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                LogUtils.tag("main").i("我改变了$propertyId")
            }
        })

        numString?.observeForever {
            LogUtils.tag(TAG).i("Observer -> numString改变了$it")
        }

        postValueNum.observeForever {
            LogUtils.tag(TAG).i("LiveData -> postValueNum改变了$it")
        }
    }
    //endregion

    fun add(v: View) {
        num.value = num.value?.plus(1)
        numString?.value = num.value.toString()
    }

    fun subtract() {
        num.value = num.value?.minus(1)
        numString?.value = num.value.toString()
    }

    //region 连续postValue测试
    fun postValueTest() {
        /**
         * 只会收到最后一次post
         */
        for (i in 1..10) {
            postValueNum.postValue(i)
        }
    }

    //使用RxJava替换LiveData
    fun <T> Observable<T>.toLiveData(): LiveData<T> = RxLiveData(this)

    class RxLiveData<T>(private val observable: Observable<T>) : LiveData<T>() {
        private var disposable: Disposable? = null

        override fun onActive() {
            disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setValue(it)
                }, {
                    setValue(null)
                })
        }

        override fun onInactive() {
            disposable?.dispose()
        }
    }
    //endregion

    var entries: SingleLiveEvent<List<String>> =
        SingleLiveEvent(mutableListOf("测试1", "测试2", "测试3", "测试4"))
    var select: SingleLiveEvent<String> = SingleLiveEvent("测试3")
}