package com.jyn.masterroad.utils.rxjava

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityRxjavaBinding

/*
 * RxJava中Observable、Flowable、Single、Maybe 有何区别?
 * [https://www.wanandroid.com/wenda/show/16634]
 *
 * RxJava 沉思录（一）：你认为 RxJava 真的好用吗？
 * https://juejin.cn/post/6844903670203547656
 *
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
 * rxjava3.0 入门到精通系列（二） 基础源码分析
 * https://juejin.cn/post/6844903935329697800
 *
 * 中文文档
 * https://github.com/mcxiaoke/RxDocs
 * https://github.com/zhaimi/RxJavaAwesome
 */
@Route(path = RoutePath.RxJava.path)
class RxJavaActivity : BaseActivity<ActivityRxjavaBinding>
    (R.layout.activity_rxjava) {

    override fun initView() {
        binding.rxJavaCreate = RxJavaCreate()
        binding.coldAndHotObservable = ColdAndHotObservable()
        binding.subjectAndProcessorTest = SubjectAndProcessorTest()
    }
}