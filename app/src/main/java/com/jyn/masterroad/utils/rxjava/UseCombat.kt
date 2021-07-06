package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.util.Random
import java.util.concurrent.TimeUnit

class UseCombat {
    companion object {
        const val TAG = "Rxjava"
    }

    fun delayTest() {
        Observable.create(ObservableOnSubscribe<Int> {
            val nextInt = Random().nextInt(10)
            LogUtils.tag("Rxjava").i("Observable nextInt: $nextInt")
            it.onNext(nextInt)
        }).flatMap {
            LogUtils.tag("Rxjava").i("Observable flatMap: $it")
            Observable.timer(it.toLong(), TimeUnit.SECONDS)
        }.flatMap {
            LogUtils.tag("Rxjava").i("Observable flatMap 第二次转换 $it")
            Observable.just("这是第二次转换")
        }.subscribe {
            LogUtils.tag("Rxjava").i("subscribe onNext: $it")
        }
    }
}