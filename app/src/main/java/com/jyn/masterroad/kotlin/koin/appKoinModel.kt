package com.jyn.masterroad.kotlin.koin

import com.jyn.masterroad.kotlin.koin.data.HelloRepository
import com.jyn.masterroad.kotlin.koin.data.HelloRepositoryImpl
import com.jyn.masterroad.kotlin.koin.data.KoinTestData
import com.jyn.masterroad.kotlin.koin.data.KoinViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appKoinModule = module {

    // HelloRepository类的单例
    single<HelloRepository> { HelloRepositoryImpl() }

    // KoinTestData对象的初始化
    factory { KoinTestData() } //get()相当于获取上面HelloRepository

    // KoinViewModel的创建
    viewModel { KoinViewModel(get()) }
}