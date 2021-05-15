package com.jyn.masterroad.kotlin.koin.data

import com.apkfuns.logutils.LogUtils

class KoinTestData {
    companion object {
        const val TAG = "Koin"
    }
    init {
        LogUtils.tag(TAG).i("KoinTestData ——> 初始化")
    }


    fun test() {
        LogUtils.tag(TAG).i("注入一个最简单的data对象")
    }
}