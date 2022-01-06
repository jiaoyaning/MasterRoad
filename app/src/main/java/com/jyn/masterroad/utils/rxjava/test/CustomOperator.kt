package com.jyn.masterroad.utils.rxjava.test

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable

/**
 * 自定义操作符
 * https://juejin.cn/post/6844903917491322893
 */
class CustomOperator {

    /**
     * 如果操作符是用于Observable发射的单独的数据项，则使用序列化操作符 [ObservableOperator]。
     * 如果操作符是用于变换Observable发射的整个数据序列，则使用变换操作符 [ObservableTransformer]。
     */
    fun myOperatorTest() {
        Observable.just(1, 2, 3)
                .lift(MyOperator.create())
                .subscribe { LogUtils.tag("main").i(it) }
    }

    fun myTransformerTest() {
        Observable.just("1234", "5678", "90qwe")
                .compose(MyTransformer.create())
                .subscribe { LogUtils.tag("main").i(it) }
    }

    /**
     * 自定义改变数据项
     * 类似 [Observable.map] 操作符
     */
    class MyOperator : ObservableOperator<String, Int> {
        companion object {
            private val instance by lazy { MyOperator() }

            @JvmStatic
            fun create() = instance
        }

        override fun apply(observer: Observer<in String>): Observer<in Int> {
            return object : Observer<Int> {
                private var mDisposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                    observer.onSubscribe(d)
                }

                override fun onNext(t: Int) {
                    if (mDisposable?.isDisposed == false) {
                        observer.onNext("$t")
                    }
                }

                override fun onError(e: Throwable) {
                    if (mDisposable?.isDisposed == false) {
                        observer.onError(e)
                    }
                }

                override fun onComplete() {
                    if (mDisposable?.isDisposed == false) {
                        observer.onComplete()
                    }
                }
            }
        }
    }


    /**
     * 自定义改变数据发射源
     * 类似 [Observable.flatMap] 操作符
     */
    class MyTransformer : ObservableTransformer<String, Char> {

        companion object {
            private val instance by lazy { MyTransformer() }

            @JvmStatic
            fun create() = instance
        }

        override fun apply(upstream: Observable<String>): ObservableSource<Char> {
            return upstream.flatMap {
                val array = it.toCharArray().toList()
                Observable.fromIterable(array)
            }
        }
    }
}