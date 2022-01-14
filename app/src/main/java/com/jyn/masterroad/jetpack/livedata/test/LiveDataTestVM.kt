package com.jyn.masterroad.jetpack.livedata.test

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.jyn.common.Base.BaseVM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import com.jyn.masterroad.kotlin.flow.FlowCreate

/*
 * https://github.com/android/architecture-components-samples/tree/main/LiveDataSample
 *
 * 面试官：你了解 LiveData 的 postValue 吗？
 * https://mp.weixin.qq.com/s/yMO1oDSUAGmPJ4w4hqSWPw
 *
 * 再谈协程之viewmodel-livedata难兄难弟
 * https://mp.weixin.qq.com/s/c36i8sTDhacHIkH8jGbMSg
 *
 * LiveData升级版 [FlowCreate]
 */
class LiveDataTestVM(application: Application) : BaseVM(application) {
    /**
     * Observable -> LiveData
     *   1. Observable 只有在数据发生改变时UI才会收到通知，
     *      而 LiveData 不同，只要你 postValue 或者 setValue ，UI 都会收到通知，不管数据有无变化
     *   2. Observable 无法感知生命周期，如果页面已销毁，可能会导致应用崩溃
     *      而 LiveData 能感知 Activity 的生命周期，在 Activity 不活动的时候不会触发(位于start状态之后)，例如一个Activity不在任务栈顶部
     *
     * LiveData -> flow
     *   1. LiveData 在连续 postValue 时，会导致数据丢失，只能收到最后有一条数据
     *   2. LiveData 不具备数据处理能力比如 Rxjava 的各种操作符，其只保留了最简单的map、switchMap等有限的操作符
     *   3. LiveData 粘性事件问题，LiveData在注册时，会触发LifecycleOwner的activeStateChanged，触发了onChange，导致在注册前的事件也被发送出来
     */

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

    private var postValueNum = object : MutableLiveData<Int>(0) {
        override fun setValue(value: Int?) {
            super.setValue(value)
            LogUtils.tag(TAG).i("setValue : $value")
        }

        /**
         * 当LiveData绑定有活跃状态的observer时就会调用
         */
        override fun onActive() {
            super.onActive()
        }

        /**
         * 当LiveData没有任何活跃状态observer绑定时调用
         */
        override fun onInactive() {
            super.onInactive()
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
         * 只会收到最后一次post，setValue则会每次都收到
         *
         * postValue 和 setValue 前，使用了同一把锁
         * 连续的post会一直抢占该锁，并重置value (volatile修饰) 值。
         * 等最后一次postValue锁释放完之后，setValue获取锁并执行setValue
         *
         * 只有一个Runnable，只是设置的是最后一次 postValue 中所重置的 value 值
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