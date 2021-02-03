package com.jyn.masterroad

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * Created by jiaoyaning on 2020/11/2.
 */
data class MainViewModel(var name: ObservableField<String>, var path: ObservableField<String>) : ViewModel() {

}