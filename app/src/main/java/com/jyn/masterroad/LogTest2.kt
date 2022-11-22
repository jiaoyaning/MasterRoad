package com.jyn.masterroad

import com.jyn.common.Utils.MLog

/**
 * Created by jiaoyaning on 2022/11/22.
 */
object LogTest2 {
    init {
        test1()
        test2("** Kotlin 方法传参 **")
    }

    private const val userid = "** Kotlin 全局变量**"

    private fun test1() {
        MLog.log("this is kotlin 方法 msg ${getUserId()}")
    }

    private fun test2(id: String) {
        val userid2 = "** Kotlin 局部变量 **"
        MLog.log("this is kotlin 全局变量 msg $userid")
        MLog.log("this is kotlin 局部变量 msg $userid2")
        MLog.log("this is kotlin 形参 msg $id")
    }


    private fun getUserId(): String {
        System.currentTimeMillis()
        return "** Kotlin 方法返回值 **"
    }
}