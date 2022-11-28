package com.jyn.masterroad

import com.jyn.common.Utils.MLog

/**
 * Created by jiaoyaning on 2022/11/22.
 */
class LogTest2 {

    companion object {
        private const val userid = "** KOTLIN 全局变量**"
        private const val test_Id = userid
    }

    private var chat_id: String = "** KOTLIN 成员变量 **"

    init {
        test1()
        test3("", "** KOTLIN 形参1 **", "")
    }

    private fun test0() {
        test3("", "** KOTLIN 形参2 **", "")
    }

    private fun test1() {
        MLog.log("this is kotlin 方法 msg ${testFun()}")
        val id = testFun()
        MLog.log("this is kotlin 方法 msg $id")
    }

    private fun test2() {
        val userid2 = "** KOTLIN 局部变量 **"
        val test_Id2 = userid2
        MLog.log("this is kotlin 全局变量 msg $test_Id")
        MLog.log("this is kotlin 局部变量 msg $test_Id2")
        val logTest2 = LogTest2()
        MLog.log("this is kotlin 对象属性 msg " + logTest2.chat_id)
    }

    private fun test3(first: String, user_id: String, last: String) {
        MLog.log("this is kotlin 形参 msg $user_id")
    }

    private fun testFun(): String {
        System.currentTimeMillis()
        System.currentTimeMillis()
        System.currentTimeMillis()
        return getUserId()
    }

    private fun getUserId(): String {
        System.currentTimeMillis()
        return "** KOTLIN 方法返回值 **"
    }
}