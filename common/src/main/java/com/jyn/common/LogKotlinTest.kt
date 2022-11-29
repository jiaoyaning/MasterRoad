package com.jyn.common

import com.jyn.common.Utils.MLog

/**
 * Created by jiaoyaning on 2022/11/22.
 */
class LogKotlinTest {

    companion object {
        private const val userid = "** KOTLIN 全局变量**"
        private const val test_Id = userid
    }

    private var chat_id: String = "** KOTLIN 成员变量 **"

    init {
        testParameter("1", getUserId(), "")
        testParameter("2", testFun(), "")
        test0()
        test1()
        test2()
    }

    private fun test0() {
        testParameter("3", testFun(), "")
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
        val logKotlinTest = LogKotlinTest()
        MLog.log("this is kotlin 对象属性 msg " + logKotlinTest.chat_id)
        testParameter("4", testFun(), "")
    }

    private fun testParameter(first: String, id: String, last: String) {
        MLog.log("this is kotlin 形参 msg $id")
    }

    private fun testFun(): String {
        System.currentTimeMillis()
        return getUserId()
    }

    private fun getUserId(): String {
        System.currentTimeMillis()
        return "** KOTLIN 方法返回值 **"
    }
}