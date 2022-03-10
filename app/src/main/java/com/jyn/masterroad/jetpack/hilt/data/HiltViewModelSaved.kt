package com.jyn.masterroad.jetpack.hilt.data

import androidx.lifecycle.*
import com.apkfuns.logutils.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
 * Android-Jetpack笔记-ViewModelSavedState
 * https://zhuanlan.zhihu.com/p/143115298
 *
 * 定义 Hilt 绑定
 * https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh_cn#define-bindings
 */
@HiltViewModel
class HiltViewModelSaved @Inject constructor(
    private val hiltSampleRepository: HiltSampleRepository,
    //SavedStateHandle 用于进程被终止时，存储和恢复数据
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY = "key"
        private const val TAG = "hilt"
    }

    init {
        LogUtils.tag(TAG).i("HiltViewModelSaved 被初始化……")
    }

    fun insert() {
        hiltSampleRepository.update("这里是HiltViewModelSaved")
    }

    // getLiveData 方法会取得一个与 key 相关联的 MutableLiveData
    // 当与 key 相对应的 value 改变时 MutableLiveData 也会更新。
    private val mutableLiveData: MutableLiveData<String> = savedStateHandle.getLiveData(KEY)

    // 对外暴露不可变的 LiveData
    val liveData: LiveData<String> = mutableLiveData
}