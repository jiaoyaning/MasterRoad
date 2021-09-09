package com.jyn.masterroad.utils.rxjava.test

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
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
    companion object {
        const val TAG = "Rxjava"
    }

    /**
     * 线程切换
     * 首先要明白，所有的 subscribe() 本质都是 subscribeActual()
     */
    fun switchThread() {
        Observable
                .create<Int> { emitter ->
                    List(1) { emitter.onNext(it) }
                    LogUtils.tag(TAG).i("create -> ${Thread.currentThread()}")
                }
                .map { it + 1 }
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    Observable
                            .create<String> { emitter ->
                                emitter.onNext("转换String$it")
                                LogUtils.tag(TAG).i("flatMap create -> ${Thread.currentThread()}")
                            }
                            .subscribeOn(Schedulers.io())
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: String) {
                        LogUtils.tag(TAG).i("subscribe onNext -> ${Thread.currentThread()}")
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })
    }
}