package com.jyn.masterroad.base

import androidx.databinding.ObservableField
import com.jyn.masterroad.MainViewModel

/**
 * Created by jiaoyaning on 2020/9/16.
 */
object RoutePath {

    /**
     * 通过反射创建Router列表
     */
    fun getRouterList(): ArrayList<MainViewModel.RouterList> {
        val routerList = arrayListOf<MainViewModel.RouterList>()
        val classes = this.javaClass.classes
        for (i in classes.indices) {
            val name: String = classes[i].getField("name")[this] as String
            val path: String = classes[i].getField("path")[this] as String
            routerList.add(MainViewModel.RouterList(ObservableField(name), ObservableField(path)))
        }
        return routerList
    }


    /**
     * 主activity
     * [com.jyn.masterroad.MainActivity].
     */
    object Main {
        const val name = "MainActivity"
        const val path = "/app/MainActivity"
    }

    /**
     * handler测试
     * [com.jyn.masterroad.handler.HandlerActivity]
     */
    object Handle {
        const val name = "HandlerActivity"
        const val path = "/app/HandlerActivity"
    }

    /**
     * handler测试
     * [com.jyn.masterroad.jetpack.livedata.LiveDataActivity]
     */
    object LiveData{
        const val name = "LiveDataActivity"
        const val path = "/app/LiveDataActivity"
    }
}