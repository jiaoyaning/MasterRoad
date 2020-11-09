package com.jyn.masterroad

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * Created by jiaoyaning on 2020/11/2.
 */
class MainViewModel : ViewModel() {

    class RouterList(var name: ObservableField<String>, var path: ObservableField<String>) {

    }
}