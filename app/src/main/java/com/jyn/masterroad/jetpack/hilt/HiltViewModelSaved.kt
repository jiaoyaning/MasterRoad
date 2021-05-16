package com.jyn.masterroad.jetpack.hilt

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*

/*
 * Android-Jetpack笔记-ViewModelSavedState
 * https://zhuanlan.zhihu.com/p/143115298
 */
class HiltViewModelSaved @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle//SavedStateHandle 用于进程被终止时，存储和恢复数据
) : ViewModel() {

    companion object {
        private const val KEY = "key"
    }


    // getLiveData 方法会取得一个与 key 相关联的 MutableLiveData
    // 当与 key 相对应的 value 改变时 MutableLiveData 也会更新。
    private val mutableLiveData: MutableLiveData<String> = savedStateHandle.getLiveData(KEY)

    // 对外暴露不可变的 LiveData
    val liveData: LiveData<String> = mutableLiveData

}