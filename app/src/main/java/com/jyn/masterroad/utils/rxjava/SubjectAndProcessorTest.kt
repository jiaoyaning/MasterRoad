package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject


/*
 * Subject和Processor
 * https://blog.csdn.net/qq_39507260/article/details/83957582
 *
 * RxJava 常见误区（一）：过度使用 Subject
 * https://www.imooc.com/article/251821
 */
class SubjectAndProcessorTest {
    companion object {
        private const val TAG = "RxJava"
    }

    private val observer = object : Observer<String> {
        override fun onSubscribe(d: Disposable?) {
            LogUtils.tag(TAG).i("--- Observer -> onSubscribe $d --- ")
        }

        override fun onNext(t: String?) {
            LogUtils.tag(TAG).i("Observer -> onNext $t")
        }

        override fun onError(e: Throwable?) {
            LogUtils.tag(TAG).i("Observer -> onError $e")
        }

        override fun onComplete() {
            LogUtils.tag(TAG).i("Observer -> onComplete")
        }
    }

    /*
     * Subject 将观察者和被观察者结合为一体，是观察者和被观察者的代理。
     *  1. Subject 是属于Hot Observable的。使用不当，例如sleep()方法，可能导致订阅者错过响应事件。
     *  2. Subject 不是线程安全的，SerializedSubject 是线程安全的
     *
     * Processor 支持背压
     */

    //region AsyncSubject
    fun asyncSubjectTest() {
        /*
         * 观察者/订阅者只会接受到onComplete之前的最后一个数据（无论在订阅前发射还是在订阅后发射）。
         */
        LogUtils.tag(TAG).i("AsyncSubject: 只会接受到onComplete之前的最后一个数据")
        val asyncSubject = AsyncSubject.create<String>()
        asyncSubject.onNext("subscribe前的 -> 第0条数据")
        asyncSubject.onNext("subscribe前的 -> 第1条数据")
        asyncSubject.onComplete()
        asyncSubject.subscribe(observer)
        asyncSubject.onNext("subscribe后的 --> 第0条数据")
        asyncSubject.onNext("subscribe后的 --> 第1条数据")
        asyncSubject.onComplete()
    }
    //endregion ================================================

    //region BehaviorSubject
    fun behaviorSubjectTest() {
        /*
         * BehaviorSubject 只会接收到订阅前最后一条发射的数据以及订阅之后所有的数据。
         */
        LogUtils.tag(TAG).i("BehaviorSubject: 只会接收到订阅前最后一条发射的数据以及订阅之后所有的数据")
        val behaviorSubject = BehaviorSubject.create<String>()
        behaviorSubject.onNext("subscribe前的 -> 第0条数据")
        behaviorSubject.onNext("subscribe前的 -> 第1条数据")
        behaviorSubject.subscribe(observer)
        behaviorSubject.onNext("subscribe后的 --> 第0条数据")
        behaviorSubject.onNext("subscribe后的 --> 第1条数据")
        behaviorSubject.onComplete()
    }
    //endregion ================================================

    //region ReplaySubject
    fun replaySubjectTest() {
        /*
         * ReplaySubject会接收到全部数据
         * ReplaySubject可以限制缓存的大小，也可以限制缓存的时间
         *  ReplaySubject.createWithSize(1);
         *  ReplaySubject.createWithTime(1000, TimeUnit.MILLISECONDS, Schedulers.newThread());
         *  ReplaySubject.createWithTimeAndSize(1000, TimeUnit.MILLISECONDS, Schedulers.newThread(), 1);
         */

        LogUtils.tag(TAG).i("ReplaySubject: 会接收到全部数据,可以限制缓存的大小&时间")
        val replaySubject = ReplaySubject.create<String>()
        replaySubject.onNext("subscribe前的 -> 第0条数据")
        replaySubject.onNext("subscribe前的 -> 第1条数据")
        replaySubject.subscribe(observer)
        replaySubject.onNext("subscribe后的 --> 第0条数据")
        replaySubject.onNext("subscribe后的 --> 第1条数据")
        replaySubject.onComplete()
    }
    //endregion ================================================

    //region PublishSubject
    fun publishSubjectTest() {
        /*
         * PublishSubject只会接收到订阅之后的所有数据。
         */

        LogUtils.tag(TAG).i("PublishSubject: 只会接收到订阅之后的所有数据")
        val publishSubject = PublishSubject.create<String>()
        publishSubject.onNext("subscribe前的 -> 第0条数据")
        publishSubject.onNext("subscribe前的 -> 第1条数据")
        publishSubject.subscribe(observer)
        publishSubject.onNext("subscribe后的 --> 第0条数据")
        publishSubject.onNext("subscribe后的 --> 第1条数据")
        publishSubject.onComplete()
    }
    //endregion ================================================
}