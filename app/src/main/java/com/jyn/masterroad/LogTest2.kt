package com.jyn.masterroad

import com.jyn.common.Utils.MLog

/**
 * Created by jiaoyaning on 2022/11/22.
 */
class LogTest2 {
    companion object {
        private const val userid = "** Kotlin 全局变量**"
    }

    var id: String = "** Kotlin 成员变量 **"

    init {
        test1()
        test2("** Kotlin 方法传参 **")
    }


    private fun test1() {
        MLog.log("this is kotlin 方法 msg ${getUserId()}")
    }

    private fun test2(id: String) {
        val userid2 = "** Kotlin 局部变量 **"
        MLog.log("this is kotlin 全局变量 msg $userid")
        MLog.log("this is kotlin 局部变量 msg $userid2")
        val logTest2 = LogTest2()
        MLog.log("this is kotlin 对象属性 msg " + logTest2.id)
        MLog.log("this is kotlin 形参 msg $id")
    }


    private fun getUserId(): String {
        System.currentTimeMillis()
        return "** Kotlin 方法返回值 **"
    }
}