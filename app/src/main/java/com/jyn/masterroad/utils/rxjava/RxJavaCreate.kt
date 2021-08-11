package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.lang.Thread.sleep

/*
 * 五种观察者模式的创建
 * https://blog.csdn.net/IT_fighter/article/details/103777107
 *
 * CompositeDisposable
 *
 * RxJava 2.0（五） Flowable和Subscriber
 * https://my.oschina.net/carbenson/blog/1002466
 *
 * RxJava2 Flowable blocking系列
 * https://blog.csdn.net/weixin_36709064/article/details/82936507
 *
 *【Android】Rxjava2 Flowable详解与背压那些事
 * https://www.jianshu.com/p/bc8fe9fa0ba1
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
    fun observerSimple() { //1. 创建被观察者(Observable)，定义要发送的事件
        val observable = Observable.just(
            "第 1 条数据", "第 2 条数据", "第 3 条数据", "第 4 条数据"
        )

        //2. 创建观察者(Observer)，接受事件并做出响应操作
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                /**
                 * Disposable英文意思是可随意使用的、用完即可丢弃的、可随意处理的，用于管理订阅关系的。
                 * 如果需要取消订阅则可以调用mDisposable.dispose()取消订阅关系
                 */
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
            it.onComplete()
            sleep(100)
            it.onNext("第 4 条数据")
        }).onErrorReturn { //可以直接捕获错误
            LogUtils.tag(TAG).i("Observable onErrorReturn: $it")
            "这是一个错误"
        }

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
     */
    fun flowableSubscriberSimple() {
        val flowable = Flowable.create(FlowableOnSubscribe<String> {
            for (i in 1..10000) {
                it.onNext("发射数据 -> $i")
            }
            it.onComplete()
        }, BackpressureStrategy.BUFFER)/*
         * MISSING  MissingBackpressureException: Queue is full?!
         * ERROR    MissingBackpressureException: create: could not emit value due to lack of requests
         * BUFFER   可以收到完整数据
         * DROP     可以发送128个
         * LATEST   会丢失数据，但是可以收到最后一个
         */
        flowable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<String> {
                override fun onSubscribe(s: Subscription) { //设置最多可接受数据的数量
                    s.request(Long.MAX_VALUE)
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
    }

    fun flowableConsumerTest() {
        Flowable.just(
            "第 1 条数据", "第 2 条数据", "第 3 条数据", "第 4 条数据"
        ).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                LogUtils.tag(TAG).i("Consumer -> onNext $it")
            }
    }

    //endregion ================================================

    //region 3. Single + SingleObserver     单次发送：onSuccess / onError
    /**
     * 只发射单个数据或错误事件，如果发射器发射多条数据，观察者只能接收到第一条数据。
     */
    fun singleTest() {
        Single.create<String> {
            it.onSuccess("这是第一条onSuccess数据") //只能发送一条数据
            it.onSuccess("这是第二条onSuccess数据") //            it.onError(Throwable("这是一条onError数据"))
        }.subscribe({
            LogUtils.tag(TAG).i("Consumer -> onSuccess $it")
        }, {
            LogUtils.tag(TAG).i("Consumer -> onError $it")
        })
    }

    //endregion ================================================

    //region 4. Completable + CompletableObserver   单次发送：onComplete / onError
    /**
     * 不发射数据，只处理 onComplete 和 onError 事件。方法onComplete与onError只可调用一个，同时调用，第一个生效。
     */
    fun completableTest() {
        Completable.create {
            it.onComplete() //            it.onError(Throwable("这是一条onError数据"))
        }.subscribe({
            LogUtils.tag(TAG).i("Completable -> onComplete")
        }, {
            LogUtils.tag(TAG).i("Completable -> onError")
        })
    }

    //endregion ================================================

    //region 5. Maybe + MaybeObserver   单次发送：onSuccess / onComplete / onError

    /*
     * 能够发射0或者1个数据，要么成功，要么失败。有点类似于Optional。
     */
    fun maybeTest() {
        Maybe.create<String> {
            it.onSuccess("这是一个onSuccess") //            it.onError(Throwable("这是一条onError数据"))
            //            it.onComplete()
        }.subscribe({
            LogUtils.tag(TAG).i("Maybe -> onSuccess")
        }, {
            LogUtils.tag(TAG).i("Maybe -> onError")
        }, {
            LogUtils.tag(TAG).i("Maybe -> onComplete")
        })
    }

    //endregion ================================================
}