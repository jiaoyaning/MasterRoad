package com.jyn.masterroad.utils.rxjava

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityRxjavaBinding

/*
 * 不要打破链式调用！一个极低成本的RxJava全局Error处理方案
 * https://juejin.cn/post/6844903711416778765
 *
 * RxJava源码解析(三)
 * https://yutiantina.github.io/2019/03/05/RxJava%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90(%E4%B8%89)-%E8%83%8C%E5%8E%8B/
 *
 * RxJava So Easy! 带你从0手撸一个RxJava TODO
 * https://mp.weixin.qq.com/s/TsyB7oXgQSCPyUP7r7SuKg
 *
 * 这一次，就彻底了解OkHttp与Retrofit吧！TODO
 * https://mp.weixin.qq.com/s/DsWoCXd-qrucW5I1Oxxdlg
 *
 * 你是否了解 RxJava 的 Disposable ？
 * https://juejin.cn/post/6978673625344049166
 *
 * 闲鱼是如何利用RxJava提升异步编程能力的
 * https://juejin.cn/post/6966869287122239501
 *
 * 大佬们，一波RxJava 3.0来袭，请做好准备~
 * https://juejin.cn/post/6844903885245513741
 *
 * 详解 RxJava 的消息订阅和线程切换原理
 * https://mp.weixin.qq.com/s/GBGlttLgQA2XrMcRTUUTOw
 *
 * 解密 RxJava 的异常处理机制，不是你想的那么简单！
 * https://mp.weixin.qq.com/s/cvRD1rstDkitm-HflwiFnQ
 *
 * 中文文档
 * https://github.com/mcxiaoke/RxDocs
 * https://github.com/zhaimi/RxJavaAwesome
 *
 * CompositeDisposable
 * 一个disposable的容器，可以容纳多个disposable，
 * 如果这个CompositeDisposable容器已经是处于dispose的状态，那么所有加进来的disposable都会被自动切断
 *
 * 装饰器模式
 */
@Route(path = RoutePath.RxJava.path)
class RxJavaActivity : BaseActivity<ActivityRxjavaBinding>(R.layout.activity_rxjava) {

    override fun initView() {
        binding.rxJavaCreate = RxJavaCreate()
        binding.coldAndHotObservable = ColdAndHotObservable()
        binding.subjectAndProcessorTest = SubjectAndProcessorTest()
        binding.useCombat = UseCombat()
        binding.rxjavaThread = RxjavaThread()
    }
}