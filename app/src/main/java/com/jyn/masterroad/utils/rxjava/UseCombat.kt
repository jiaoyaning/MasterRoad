package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import java.util.Random
import java.util.concurrent.TimeUnit

class UseCombat {
    companion object {
        const val TAG = "Rxjava"
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


    //disposable

    fun test() {
        //无延迟,无后序
        Single.just(1)
            .map { it.toString() }
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onSuccess(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }
            })

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

        //无延迟，有后续
        Observable.just(1)
            .map { it.toString() }
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }
}