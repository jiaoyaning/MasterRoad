package com.jyn.masterroad.utils.rxjava.test

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.qualifier.named

/*
 * 线程调度器
 * https://juejin.cn/post/6844903927599611912#heading-6
 *
 * Android:随笔——RxJava的线程切换
 * https://www.jianshu.com/p/d9da64774f7b
 *
 * 详解 RxJava2 的线程切换原理
 * https://www.jianshu.com/p/a9ebf730cd08
 *
 * RxJava 容易忽视的细节: subscribeOn() 方法没有按照预期地运行
 * https://mp.weixin.qq.com/s/pVT6YuUT1gwyAkwqkX8H6A
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

    /**
     * 手动切换发射线程
     */
    fun emitterSwitchThread() {
        Observable
            .create<Int> { emitter ->
                emitter.onNext(1)
                Thread {
                    emitter.onNext(2)
                }.apply { name = "test1" }.start()

                Thread {
                    named("子线程2")
                    emitter.onNext(3)
                    emitter.onNext(4)
                }.apply { name = "test1" }.start()
            }
            .subscribeOn(Schedulers.io())
            .map {
                LogUtils.tag(TAG).i("map -> $it -> ${Thread.currentThread().name}")
                it
            }
            .subscribe {
                LogUtils.tag(TAG).i("subscribe onNext -> $it -> ${Thread.currentThread().name}")
            }
    }
}