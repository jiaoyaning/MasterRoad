package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.lang.Thread.sleep

/*
 * 五种观察者模式的创建
 * https://blog.csdn.net/IT_fighter/article/details/103777107
 *
 * CompositeDisposable
 */
class RxJavaCreate {
    companion object {
        private const val TAG = "RxJava"
    }

//region 1. Observable + Observer/Consumer  发射数据
    /*
     * Observable: 能够发射0或n个数据，并以成功或错误事件终止
     */

    //region 1.1 观察者 Observable.subscribe(observer)
    fun observerSimple() {
        //1. 创建被观察者(Observable)，定义要发送的事件
        val observable = Observable.just(
            "第 1 条数据",
            "第 2 条数据",
            "第 3 条数据",
            "第 4 条数据"
        )

        //2. 创建观察者(Observer)，接受事件并做出响应操作
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                LogUtils.tag(TAG).i("Observer -> onSubscribe $d")
            }

            override fun onNext(s: String) {
                LogUtils.tag(TAG).i("Observer -> onNext : $s")
            }

            override fun onError(e: Throwable) {
                LogUtils.tag(TAG).i("Observer -> onError : $e")
            }

            override fun onComplete() {
                LogUtils.tag(TAG).i("Observer -> onComplete")
            }
        }

        //3. 观察者通过订阅（subscribe）被观察者把它们连接到一起。
        observable.subscribe(observer)
    }
    //endregion

    //region 1.2. 消费者 Observable.subscribe(consumer)
    fun consumerSimple() {
        val observable = Observable.create(ObservableOnSubscribe<String> {
            LogUtils.tag(TAG).i("onNext Thread:${Thread.currentThread().name}")
            it.onNext("第 1 条数据")
            sleep(100)
            it.onNext("第 2 条数据")
            sleep(100)
            it.onNext("第 3 条数据")
            sleep(100)
            it.onNext("第 4 条数据")
        })

        //创建消费者(Consumer)，接受并消费事件
        val consumer: Consumer<String> = Consumer { t ->
            LogUtils.tag(TAG).i("accept:$t ； Thread:${Thread.currentThread().name}")
        }

        val disposable = observable.subscribe(consumer)

        LogUtils.tag(TAG).i("consumer sleep前 disposable: ${disposable.isDisposed}")
        sleep(500)
        LogUtils.tag(TAG).i("consumer sleep后 disposable: ${disposable.isDisposed}")
    }
    //endregion
//endregion ================================================

//region 2. Flowable + Subscriber   背压
    /*
     * 能够发射0或n个数据，并以成功或错误事件终止。 支持背压(下游处理速度比上游发送速度要慢)，可以控制数据源发射的速度。
     *
     *
     */
    fun flowableSimple() {
        val flowable = Flowable.just(
            "第 1 条数据",
            "第 2 条数据",
            "第 3 条数据",
            "第 4 条数据"
        )

        flowable.subscribe(object : Subscriber<String> {
            override fun onSubscribe(s: Subscription?) {
                LogUtils.tag(TAG).i("Subscriber -> onSubscribe $s")
            }

            override fun onNext(t: String?) {
                LogUtils.tag(TAG).i("Subscriber -> onNext $t")
            }

            override fun onError(t: Throwable?) {
                LogUtils.tag(TAG).i("Subscriber -> onError $t")
            }

            override fun onComplete() {
                LogUtils.tag(TAG).i("Subscriber -> onComplete")
            }
        })

//        flowable.subscribe {
//            LogUtils.tag(TAG).i("Consumer -> onNext $it")
//        }
    }

//endregion ================================================


//region 3. Single + SingleObserver     单次发送：onSuccess / onError
    /*
     * 只发射单个数据或错误事件，如果发射器发射多条数据，观察者只能接收到第一条数据。
     */

//endregion ================================================

//region 4. Completable + CompletableObserver   单次发送：onComplete / onError
    /**
     * 不发射数据，只处理 onComplete 和 onError 事件。方法onComplete与onError只可调用一个，同时调用，第一个生效。
     */

//endregion ================================================

//region 5. Maybe + MaybeObserver   单次发送：onSuccess / onComplete / onError
    /**
     * 能够发射0或者1个数据，要么成功，要么失败。有点类似于Optional。
     */

//endregion ================================================
}