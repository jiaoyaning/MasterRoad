package com.jyn.masterroad.app

import com.apkfuns.logutils.LogUtils

class StatisticKotlin {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            LogUtils.tag("main").i("--> 打印")
        }
    }

    private fun logTest2() {
        LogUtils.tag("main").i("--> 打印 logTest1")
    }
}