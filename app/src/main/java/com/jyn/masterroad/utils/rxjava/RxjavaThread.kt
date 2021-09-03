package com.jyn.masterroad.utils.rxjava

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.single.SingleObserveOn
import io.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn
import io.reactivex.rxjava3.internal.schedulers.IoScheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/*
 * 线程调度器
 * https://juejin.cn/post/6844903927599611912#heading-6
 *
 * Android:随笔——RxJava的线程切换
 * https://www.jianshu.com/p/d9da64774f7b
 *
 * 详解 RxJava2 的线程切换原理
 * https://www.jianshu.com/p/a9ebf730cd08
 */
class RxjavaThread {
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
}