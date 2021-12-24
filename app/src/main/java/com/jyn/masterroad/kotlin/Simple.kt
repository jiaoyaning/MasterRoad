package com.jyn.masterroad.kotlin

import kotlin.coroutines.*

class Simple {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            simple()
        }

        private fun simple() {
            val suspend = suspend {
                val suspendCoroutine = suspendCoroutine<String> { it.resume("挂起点恢复") } //会直接挂起
                "Hello world! createCoroutine:$suspendCoroutine"
            }.startCoroutine(object : Continuation<String> { //创建并启动
                override val context: CoroutineContext
                    get() = EmptyCoroutineContext

                override fun resumeWith(result: Result<String>) {
//                    LogUtils.tag(TAG).i()
                    println("resumeWith：$result")
                }
            })

        }
    }
}