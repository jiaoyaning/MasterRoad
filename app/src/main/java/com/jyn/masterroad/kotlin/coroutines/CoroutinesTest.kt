package com.jyn.masterroad.kotlin.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

/*
 * https://aisia.moe/2018/02/08/kotlin-coroutine-kepa/
 *
 *
 * Kotlin协程是 callback 的语法糖，它的主要好处是能让你写不需要 callback 的异步代码。换言之，把异步代码写得看起来就想同步的一样。
 */

/**
 * suspend 的 [main] 本质是这个 [kotlin.coroutines.jvm.internal.runSuspend]
 */
suspend fun main() {
    println("This is executed before the delay")
    stallForTime()
    println("This is executed after the delay")
}

suspend fun stallForTime() {
    withContext(Dispatchers.Default) {
        delay(2000L)
    }
}

//挂起函数，同步返回
suspend fun suspendFun1(a: Int) {
    return
}

/**
 * 挂起函数，异步返回
 * java调用该方法时需要额外传递一个[Continuation]对象
 * 其实所以的协程本质上都是 Continuation
 */
suspend fun suspendFun2(a: String) = suspendCoroutine { continuation: Continuation<Int> -> //此处会挂起
    thread {
        continuation.resumeWith(Result.success(10))
    }
}