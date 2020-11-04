package com.jyn.masterroad

import android.view.View
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by jiaoyaning on 2020/11/2.
 */
class MainViewModel : ViewModel() {
    val handler = "Handler"

    fun goToHandler(view: View) {
        ARouter.getInstance().build(handler).navigation();
    }
}