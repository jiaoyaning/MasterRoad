package com.jyn.masterroad.jetpack.hilt

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

/**
 * Created by jiaoyaning on 2021/5/14.
 */
class HiltViewModel @ViewModelInject constructor() : ViewModel() {
    val liveData = liveData {
        emit(" i am a ViewModelInject")
    }
}