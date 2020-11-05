package com.jyn.masterroad

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.jyn.masterroad.base.RoutePath

/**
 * Created by jiaoyaning on 2020/11/2.
 */
class MainViewModel : ViewModel() {

    val handler = "Handler"

    fun goToHandler(view: View) {
        ARouter.getInstance().build(RoutePath.HANDLER).navigation();
    }

    class RouterList {
        var name: ObservableField<String> = ObservableField()
        var path: ObservableField<String> = ObservableField()
    }
}