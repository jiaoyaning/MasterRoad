package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import java.util.Random
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.internal.operators.single.SingleJust
import io.reactivex.rxjava3.internal.operators.single.SingleMap


class UseCombat {
    companion object {
        const val TAG = "Rxjava"
    }

    /**
     * 无延迟,无后序
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
            .map { it.toString() } //返回SingleMap对象
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onSuccess(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }
            })

    }

    fun test3() {
        //有延迟,无后序
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

    fun test4() {
        //有延迟有后续

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