package com.jyn.masterroad.rxjava

import android.annotation.SuppressLint
import com.apkfuns.logutils.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.lang.Thread.sleep

class RxJavaCreate {
    companion object {
        private const val TAG = "RxJava"
    }

    /**
     * 完全体
     */
    fun simple() {
        //1. 创建被观察者(Observable)，定义要发送的事件
        val observable = Observable.just(
                "第 1 条数据",
                "第 2 条数据",
                "第 3 条数据",
                "第 4 条数据")

        //2. 创建观察者(Observer)，接受事件并做出响应操作
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                LogUtils.tag(TAG).i("onSubscribe")
            }

            override fun onNext(s: String) {
                LogUtils.tag(TAG).i("onNext : $s")
            }

            override fun onError(e: Throwable) {
                LogUtils.tag(TAG).i("onError : $e")
            }

            override fun onComplete() {
                LogUtils.tag(TAG).i("onComplete")
            }
        }

        //3. 观察者通过订阅（subscribe）被观察者把它们连接到一起。
        observable.subscribe(observer)
        sleep(1000)


        //1 创建消费者(Consumer)，接受并消费事件
        val consumer: Consumer<String> = Consumer { t ->
            LogUtils.tag(TAG).i("accept : $t")
        }
        val disposable = observable.subscribe(consumer)

        LogUtils.tag(TAG).i("consumer disposable : ${disposable.isDisposed}")
    }

    fun chainedSimple() {

    }
}