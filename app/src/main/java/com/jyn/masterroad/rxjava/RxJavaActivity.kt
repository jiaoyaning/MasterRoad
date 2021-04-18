package com.jyn.masterroad.rxjava

import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityRxjavaBinding
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
 * RxjavaPlugins
 * https://blog.csdn.net/weixin_43724742/article/details/103394113
 */
@Route(path = RoutePath.RxJava.path)
class RxJavaActivity : BaseActivity<ActivityRxjavaBinding>(R.layout.activity_rxjava) {

    override fun initView() {
        simpleRxJava()

    }

    private fun simpleRxJava() {
        //步骤1. 创建被观察者(Observable),定义要发送的事件。

        val observable = Observable.just("第 1 条数据", "第 2 条数据",
                "第 3 条数据", "第 4 条数据")

        // region Observable.create()所创建的 observable 对象
//        val observable = Observable.create<String> {
//            object : ObservableOnSubscribe<String> {
//                override fun subscribe(emitter: ObservableEmitter<String>) {
//                    emitter.onNext("文章1")
//                    emitter.onNext("文章2")
//                    emitter.onNext("文章3")
//                    emitter.onComplete()
//                }
//            }
//        }.map<String>(object : Function<String?, String?> {
//            @Throws(Exception::class)
//
//            override fun apply(t: String): String? {
//                return t
//            }
//        })
        // endregion

        //步骤2. 创建观察者(Observer)，接受事件并做出响应操作。
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                LogUtils.tag("main").i("onSubscribe")
            }

            override fun onNext(s: String) {
                LogUtils.tag("main").i("onNext : $s")
            }

            override fun onError(e: Throwable) {
                LogUtils.tag("main").i("onError : $e")
            }

            override fun onComplete() {
                LogUtils.tag("main").i("onComplete")
            }
        }

        //步骤3. 观察者通过订阅（subscribe）被观察者把它们连接到一起。
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}