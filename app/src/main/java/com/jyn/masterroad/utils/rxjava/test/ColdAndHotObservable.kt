package com.jyn.masterroad.utils.rxjava.test

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

/*
 * Cold Observable 和 Hot Observable
 * https://www.jianshu.com/p/12fb42bcf9fd
 *
 * 【译】RxJava中的事件广播
 * https://www.jianshu.com/p/19c946e32596
 */
class ColdAndHotObservable {
    companion object {
        private const val TAG = "RxJava"
    }
    /*
     * 1. Cold Observable (冷流) 只有 Subscriber 订阅时，才开始执行发射数据流的代码。
     *  并且 Cold Observable (冷流) 和 Subscriber 只能是一对一的关系，当有多个不同的订阅者时，消息是重新完整发送的。
     *  也就是说对 Cold Observable 而言，有多个Subscriber的时候，他们各自的事件是独立的。
     *
     * 2. Hot Observable (热流) 无论有没有 Subscriber 订阅，事件始终都会发生。
     *  当 Hot Observable 有多个订阅者时，Hot Observable 与订阅者们的关系是一对多的关系，可以与多个订阅者共享信息。
     *
     * 注：Cold可以转为Hot，但Hot无法转为Cold
     */

    //region 1. Cold Observable (冷流)
    fun coldObservable() {
        /*
         * Observable 的 just、create、range、fromXXX 等操作符都能生成Cold Observable
         *  subscriber1 和 subscriber2 的结果并不一定是相同的，二者完全独立
         */
        val subscriber1 = Consumer<Long> { aLong -> LogUtils.tag(TAG).i("subscriber1: $aLong") }
        val subscriber2 = Consumer<Long> { aLong -> LogUtils.tag(TAG).i("  subscriber2: $aLong") }

        val observable: Observable<Long> = Observable
            .create(ObservableOnSubscribe<Long> { e ->
                Observable.intervalRange(0, 10, 0, 10, TimeUnit.MILLISECONDS)
                    .subscribe { e.onNext(it) }
            }).observeOn(Schedulers.newThread())

        observable.subscribe(subscriber1)
        sleep(30)
        observable.subscribe(subscriber2) //subscriber2随时订阅，随时可以接收
    }
    //endregion

    //region 2. Hot Observable (热流)
    fun hotObservable() {
        /**
         * 使用 publish 操作符，可以让 Cold Observable 转换成 Hot Observable。
         * 它将原先的 Observable 转换成 ConnectableObservable。
         */
        val subscriber1 = Consumer<Long> { aLong -> LogUtils.tag(TAG).i("subscriber1: $aLong") }
        val subscriber2 = Consumer<Long> { aLong -> LogUtils.tag(TAG).i("  subscriber2: $aLong") }
        val subscriber3 = Consumer<Long> { aLong -> LogUtils.tag(TAG).i("   subscriber3: $aLong") }
        val observable: ConnectableObservable<Long> = Observable
            .create(ObservableOnSubscribe<Long> { e ->
                Observable.intervalRange(0, 10, 0, 10, TimeUnit.MILLISECONDS)
                    .subscribe { e.onNext(it) }
            })
            .observeOn(Schedulers.newThread())
            .publish()
        /**
         * 多个订阅的 Subscriber 共享同一事件，
         * 生成的 ConnectableObservable 需要调用connect()才能真正执行。
         *
         * 执行后并不会等待未被subscribe的观察者，半路所subscribe的观察者错过后并不会补上
         */
        observable.connect()

        observable.subscribe(subscriber1)
        sleep(50L)
        observable.subscribe(subscriber2)
        sleep(90L)
        observable.subscribe(subscriber3)
    }
    //endregion
}