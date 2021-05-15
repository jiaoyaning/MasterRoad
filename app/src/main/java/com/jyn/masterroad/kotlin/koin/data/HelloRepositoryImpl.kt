package com.jyn.masterroad.kotlin.koin.data

import com.apkfuns.logutils.LogUtils

class HelloRepositoryImpl : HelloRepository {
    init {
        LogUtils.tag(KoinTestData.TAG).i("HelloRepositoryImpl ——> 初始化")
    }

    override fun getData(): String = "HelloRepositoryImpl ——> getData()"
    override fun getNum(): Int = 0
}