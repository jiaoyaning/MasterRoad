package com.jyn.masterroad.jetpack.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    var test: MutableLiveData<String>? = null
}