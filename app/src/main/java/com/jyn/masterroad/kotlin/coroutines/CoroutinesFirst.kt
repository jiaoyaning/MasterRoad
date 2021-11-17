package com.jyn.masterroad.kotlin.coroutines

import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.coroutines.*

/**
 * https://aisia.moe/2018/02/08/kotlin-coroutine-kepa/ 极好
 *
 * Kotlin协程是 callback 的语法糖，它的主要好处是能让你写不需要 callback 的异步代码。
 * 换言之，把异步代码写得看起来就想同步的一样。
 *
 * [Continuation]是一个非常重要的概念，suspend 方法在经过 CPS变换后，会自动给方法添加一个[Continuation]参数
 * 这个参数保存了挂起点信息，协程上下文，以及状态机
 */

const val TAG = "Coroutines"

/**
 * suspend 的 [main] 本质是这个 [kotlin.coroutines.jvm.internal.runSuspend]
 */
suspend fun main() {
    println("This is executed before the delay")
    withContext(Dispatchers.Default) {
        delay(2000L)
    }
    println("This is executed after the delay")
}

/**
 * 用基础库来创建协程
 * 1.[createCoroutine]
 * 2.[startCoroutine]
 */
fun createCoroutineTest1() {
    //1.用 createCoroutine 来创建协程，需要 resume 方法启动
    val coroutine: Continuation<Unit> = suspend { //suspend{} 是协程的主体
        delayFun()
        "Hello world! createCoroutine"
    }.createCoroutine(object : Continuation<String> { //Continuation 表示协程执行完之后要执行的代码
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            LogUtils.tag(TAG).i(result.getOrThrow())
        }
    })
    coroutine.resume(Unit) //启动协程
}
fun createCoroutineTest2() {
    //2.直接用 startCoroutine 来创建并启动协程
    suspend {
        delayFun()
        "Hello world! createCoroutine"
    }.startCoroutine(object : Continuation<String> { //创建并启动
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            LogUtils.tag(TAG).i(result.getOrThrow())
        }
    })
}
suspend fun delayFun(){
    delay(1000)
}

/**
 * 暂停一个协程
 * [suspendCoroutine]
 */
fun suspendCoroutineFun() {
    suspend {
        LogUtils.tag(TAG).i("before suspend")
        suspendCoroutine<Unit> { } //会直接挂起
        LogUtils.tag(TAG).i("after suspend")
    }.startCoroutine(object : Continuation<Any> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Any>) {
            LogUtils.tag(TAG).i(result.getOrThrow())
        }
    })

    //suspendCoroutine挂起后，使用resume恢复
    suspend {
        LogUtils.tag(TAG).i("before suspend")
        val resumeValue = suspendCoroutine<String> { continuation ->
            sleep(3000)
            continuation.resume("挂起点恢复")
        }
        LogUtils.tag(TAG).i("after suspend $resumeValue")
    }.startCoroutine(object : Continuation<Any> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Any>) {
            LogUtils.tag(TAG).i(result.getOrThrow())
        }
    })
}


/**
 * 协程上下文
 * [CoroutineContext]
 */
fun coroutineContextFun() {
    suspend {
        delay(2000)
        "Hello world! coroutineContext ${coroutineContext[CoroutineName]}"
    }.startCoroutine(object : Continuation<String> {
        override val context: CoroutineContext
            get() = CoroutineName("HHH(自定义协程名字)") +
                    //协程异常处理器
                    CoroutineExceptionHandler { coroutineContext, throwable ->
                        LogUtils.tag(TAG).i(coroutineContext)
                        throwable.printStackTrace()
                        LogUtils.tag(TAG).i(throwable.message)
                    }

        override fun resumeWith(result: Result<String>) {
            result.onFailure {
                context[CoroutineExceptionHandler]?.handleException(context, it)
            }
        }
    })
}

/**
 * 协程拦截器 拦截挂起点 (也需要添加到协程上下文中)
 * [ContinuationInterceptor]
 */
fun continuationInterceptorFun() {

    class LogContinuation<T>(val continuation: Continuation<T>, override val context: CoroutineContext) : Continuation<T> {
        override fun resumeWith(result: Result<T>) {
            LogUtils.tag(TAG).i("拦截前：$result")
            continuation.resumeWith(result)
            LogUtils.tag(TAG).i("拦截后：$result")
        }
    }

    suspend {
        delay(2000)
        "Hello world!"
    }.startCoroutine(object : Continuation<String> {
        override val context: CoroutineContext
            get() = CoroutineName("HHH(自定义协程名字)") +
                    object : ContinuationInterceptor {
                        override val key: CoroutineContext.Key<*>
                            get() = ContinuationInterceptor

                        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
                            return LogContinuation(continuation, context)
                        }
                    }

        override fun resumeWith(result: Result<String>) {
            LogUtils.tag(TAG).i(result)
        }
    })
}