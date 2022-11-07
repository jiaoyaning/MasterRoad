package com.jyn.masterroad.kotlin.koin

import com.jyn.masterroad.kotlin.koin.data.*
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind

/*
 * https://juejin.cn/post/6844904202586554382#heading-5
 *
 * Bind 是一个中缀函数，可用于绑定实现了多接口的类
 * Scope 用于控制对象在 Koin 内的生命周期。single 与 factory 都是 scope。自定义Scope时，需要手动close
 *   1. single 创建的对象在整个容器的生命周期内都是存在的，因此任意地方注入都是同一实例。
 *   2. factory 每次都创建新的对象，因此它不被保存，也不能共享实例。
 */
val appKoinModule = module {

    /*
     * 全局作用域下单例模式，默认懒加载，一旦创建整个koin生命周期内都存在。
     */
    single<HelloRepository> { HelloRepositoryImpl() } //注入无参数单例

    single { ParameterData() }  //使用覆盖放式注入

    single(named("Special")) { ParameterData("限定符1", "限定符2") } //相同类型注入使用限定符
    //注入多个参数，并且需要引用已注入的单例时 用get()获取
    single(named("more")) { (parameter1: String, parameter2: String) ->
        ParameterData(parameter1, parameter2)
    }


    // KoinTestData对象的初始化
    factory { KoinTestData() } //get()相当于获取上面HelloRepository

    // KoinViewModel的创建
    viewModel { KoinViewModel(get()) }
}