package com.jyn.masterroad.kotlin.koin.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext

/*
 * 当我们其他类中使用时需要实现 KoinComponent 接口或者直接 GlobalContext 类获取
 */
class KoinViewModel(repo: HelloRepository) : ViewModel(), KoinComponent {
    companion object {
        const val TAG = "Koin"
    }

    private val parameterData1: ParameterData = get()
    private val parameterData2: ParameterData = GlobalContext.get().get()

    var num: MutableLiveData<Int> = MutableLiveData(0)

    init {
        LogUtils.tag(TAG)
            .i("KoinTestData ——> 初始化 ${repo.getData()} ; $parameterData1 ; $parameterData2")
    }

    fun add() {
        num.value = num.value?.plus(1)
    }
}