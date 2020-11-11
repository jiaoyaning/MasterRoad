package com.jyn.masterroad.jetpack.livedata

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataTestViewModel : ViewModel() {
    var num: MutableLiveData<Int> = MutableLiveData(0)

    fun add(v: View) {
        num.value = num.value?.plus(1);
    }

    fun add() {
        num.value = num.value?.plus(1);
    }

    fun subtract(){
        num.value = num.value?.minus(1)
    }
}