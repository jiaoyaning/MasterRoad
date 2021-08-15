package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import java.util.Random
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.internal.operators.single.SingleJust
import io.reactivex.rxjava3.internal.operators.single.SingleMap
import io.reactivex.rxjava3.internal.operators.single.SingleDelay
import io.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn
import io.reactivex.rxjava3.internal.operators.single.SingleObserveOn
import io.reactivex.rxjava3.internal.operators.observable.ObservableInterval
import io.reactivex.rxjava3.internal.operators.observable.ObservableMap
import io.reactivex.rxjava3.internal.operators.observable.ObservableDelay
import io.reactivex.rxjava3.schedulers.Schedulers

class UseCombat {
    companion object {
        const val TAG = "Rxjava"
    }

    /**
     * 总结两点
     * 1.生产者有没有来自上游的任务，而非自己生产
     * 2.生产者有没有自己生产的后序任务
     */

    /**
     * 无上游 无延迟,无后序
     */
    fun test1() {
        /**
         * Single.just() 返回一个 [SingleJust]对象
         * subscribe() 方法其实就是 [SingleJust.subscribeActual]方法
         */
        Single.just(1) //SingleJust对象
            .subscribe( // subscribeActual方法
                object : SingleObserver<Int> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onSuccess(t: Int?) {
                    }

                    override fun onError(e: Throwable?) {
                    }
                })

        Observable.just(1)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: Int?) {
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }

    /**
     * 无延迟,有后序
     */
    fun test2() {
        /**
         * Single.just() 返回一个 [SingleJust]对象
         * map 穿入SingleJust对象，返回 [SingleMap]对象
         */
        Single.just(1) //返回SingleJust对象
            .map { it.toString() } //返回SingleMap对象，包含了上一级的singleJust对象
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onSuccess(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }
            })

    }

    /**
     * 有延迟,无后序
     * [SingleDelay.subscribeActual]
     */
    fun test3() {
        Single.just("1")
            .delay(1, TimeUnit.SECONDS)
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onSuccess(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }
            })
    }

    /**
     * 无上游 有延时，有后序
     * [ObservableInterval.subscribeActual]
     * [ObservableDelay.subscribeActual]
     * [ObservableMap.subscribeActual]
     */
    fun test4() {
        Observable.interval(1, TimeUnit.SECONDS)
            .delay(1, TimeUnit.SECONDS)
            .map { it.toLong() }
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: Long?) {
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }


    /**
     * 线程切换
     * subscribeOn = [SingleSubscribeOn] [SingleSubscribeOn.subscribeActual]
     * observeOn =   [SingleObserveOn] [SingleObserveOn.subscribeActual]
     */
    fun switchThread() {
        Single.just(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onSuccess(t: Int?) {
                }

                override fun onError(e: Throwable?) {
                }
            })
    }

    fun delayTest() {
        Observable
            .create(ObservableOnSubscribe<Int> {
                val nextInt = Random().nextInt(10)
                LogUtils.tag("Rxjava").i("Observable nextInt: $nextInt")
                it.onNext(nextInt)
            })
            .flatMap {
                LogUtils.tag("Rxjava").i("Observable flatMap: $it")
                Observable.timer(it.toLong(), TimeUnit.SECONDS)
            }
            .flatMap {
                LogUtils.tag("Rxjava").i("Observable flatMap 第二次转换 $it")
                Observable.just("这是第二次转换")
            }
            .subscribe {
                LogUtils.tag("Rxjava").i("subscribe onNext: $it")
            }
    }
}