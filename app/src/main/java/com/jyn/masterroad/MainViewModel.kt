package com.jyn.masterroad

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.apkfuns.logutils.LogUtils

/**
 * data class 会自动重写下面方法
 *
 * equals()
 * hashCode()
 * toString()
 * copy()
 *
 * componentN() //数据类自带解构方法
 *
 */
data class MainViewModel(var name: ObservableField<String>, var path: ObservableField<String>) : ViewModel() {

}

/**
 * 解构方法测试
 */
fun main() {
    val test = MainViewModel(ObservableField("测试name"), ObservableField("测试path"))
    val (a, b) = test
    println("a:${a.get()} ; b:${b.get()}")
}