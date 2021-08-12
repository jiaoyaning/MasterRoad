package com.jyn.masterroad.utils.rxjava

import com.apkfuns.logutils.LogUtils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.util.Random
import java.util.concurrent.TimeUnit

/*
 * 【译】对RxJava中.repeatWhen()和.retryWhen()操作符的思考
 * https://www.jianshu.com/p/023a5f60e6d0
 *
 * 【译】避免打断链式结构：使用.compose( )操作符
 * https://www.jianshu.com/p/e9e03194199e
 */
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
}