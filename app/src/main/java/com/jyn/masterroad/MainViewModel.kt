package com.jyn.masterroad

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jyn.common.ARouter.RoutePath

/**
 * data class 会自动重写下面方法
 *
 * equals()
 * hashCode()
 * toString()
 * copy()
 *
 * componentN() //数据类自带解构方法
 */
data class MainViewModel(var name: ObservableField<String>, var path: ObservableField<String>, var span: Int = 1) : ViewModel() {

    companion object {
        /**
         * 通过反射创建Router列表
         */
        fun getRouterList(): ArrayList<MainViewModel> {
            val routerList = arrayListOf<MainViewModel>()
            val classes = RoutePath.javaClass.classes
            for (i in classes.indices) {
                val name: String = classes[i].getField("name")[this] as String
                val path: String = classes[i].getField("path")[this] as String
                val span: Int = classes[i].getField("span")[this] as Int
                routerList.add(MainViewModel(ObservableField(name), ObservableField(path), span))
            }
            return routerList
        }
    }
}