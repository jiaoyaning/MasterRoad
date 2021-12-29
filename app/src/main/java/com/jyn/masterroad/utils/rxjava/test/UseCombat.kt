package com.jyn.masterroad.utils.rxjava.test

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.observable.*
import java.util.Random
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.internal.operators.single.SingleJust
import io.reactivex.rxjava3.internal.operators.single.SingleMap
import io.reactivex.rxjava3.internal.operators.single.SingleDelay
import io.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn
import io.reactivex.rxjava3.internal.operators.single.SingleObserveOn
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.internal.schedulers.IoScheduler

/**
 * 使用示例 & 源码分析
 */
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



        /**
         * [ObservableJust.subscribeActual]
         */
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


        /**
         * [ObservableCreate.subscribeActual]
         * subscribe的时候，把[ObservableEmitter]和[Observer]两个对象合并成了一个
         */
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
        }).subscribe(object : Observer<Int> {
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
         * [Single.just] 返回一个 [SingleJust] 对象
         * [map] 传入上游 [SingleJust] 对象 & 自己本身，包装成一个 [SingleMap]对象
         *
         * 而subscribe()方法等同于[SingleMap.subscribeActual]方法，在该方法中
         * [SingleJust] 对象订阅了由 map(Function) 和下游 SingleObserver 组成的[SingleMap.MapSingleObserver]对象
         * 这样，在[SingleJust]发送事件的时候，就可以由 map(Function)方法执行完，再发送给[SingleObserver]对象
         *
         * map所创建的[SingleMap]对象中，并不处理[Disposable]，[SingleObserver]所拿到的[Disposable]始终是Map上游也就是[SingleJust]的
         * 所以当[Disposable]取消订阅的时候，just会停止发送数据，map就接受不到数据了，也没办法再继续处理
         */
        Single.just(1) //返回SingleJust对象
                .map { it.toString() } //把 SingleJust对象 以及自身的 Function 包装成SingleMap对象
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
     * [SingleDelay.subscribeActual] 原理同上 差别在于[Disposable]
     * 试想，[Single.just]发送完数据后，[Single.delay]正在阻塞的过程中时[SingleObserver]调用[Disposable.dispose]取消了订阅，该当如何
     *
     * [Single.delay]后的[Single.subscribe]订阅方法其实对应[SingleDelay.subscribeActual]
     * [SingleDelay]会暂时创建一个[SequentialDisposable]来占位，等上游调用[onSubscribe]传递[Disposable]时,
     *  再用这个上游的[Disposable]代替自己，并传递给下游，
     *
     * 其他操作符应该也大致相同，这样就能保证往下游传递的[Disposable]始终只有一个，就是最上游的那一个
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
     * [ObservableFlatMap.subscribeActual]
     */
    fun test4() {
        Observable.interval(1, TimeUnit.SECONDS)
                .delay(1, TimeUnit.SECONDS)
                .map { it.toLong() }
                .flatMap { Observable.just(it + 1) }
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
     * subscribeOn() =      [SingleSubscribeOn] [SingleSubscribeOn.subscribeActual]
     * Schedulers.io() =    [IoScheduler]
     * observeOn() =        [SingleObserveOn] [SingleObserveOn.subscribeActual]
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