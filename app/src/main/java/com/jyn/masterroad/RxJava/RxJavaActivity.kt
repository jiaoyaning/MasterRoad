package com.jyn.masterroad.RxJava

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * RxJava中Observable、Flowable、Single、Maybe 有何区别?
 * [https://www.wanandroid.com/wenda/show/16634]
 *
 * RxJava 沉思录（一）：你认为 RxJava 真的好用吗？
 * https://juejin.cn/post/6844903670203547656
 *
 * 不要打破链式调用！一个极低成本的RxJava全局Error处理方案
 * https://juejin.cn/post/6844903711416778765
 */
@Route(path = RoutePath.RxJava.path)
class RxJavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
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