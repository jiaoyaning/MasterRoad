package com.jyn.masterroad

import com.jyn.common.Utils.MLog

/**
 * Created by jiaoyaning on 2022/11/22.
 */
class LogTest2 {

    companion object {
        private const val userid = "** KOTLIN 全局变量**"
    }

    var id: String = "** KOTLIN 成员变量 **"

    init {
        test1()
        test3("", "** KOTLIN 形参1 **", "")
    }

    private fun test0() {
        test3("", "** KOTLIN 形参2 **", "")
    }

    private fun test1() {
        MLog.log("this is kotlin 方法 msg ${getUserId()}")
        val userId = getUserId()
        MLog.log("this is kotlin 方法 msg $userId")
    }

    private fun test2() {
        val userid2 = "** KOTLIN 局部变量 **"
        MLog.log("this is kotlin 全局变量 msg $userid")
        MLog.log("this is kotlin 局部变量 msg $userid2")
        val logTest2 = LogTest2()
        MLog.log("this is kotlin 对象属性 msg " + logTest2.id)
    }

    private fun test3(first: String, id: String, last: String) {
        MLog.log("this is kotlin 形参 msg $id")
    }

    private fun getUserId(): String {
        System.currentTimeMillis()
        return "** KOTLIN 方法返回值 **"
    }
}