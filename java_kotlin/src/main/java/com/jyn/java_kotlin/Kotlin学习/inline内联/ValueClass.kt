package com.jyn.java_kotlin.Kotlin学习.inline内联

/*
 * Kotlin 宣布一个重磅特性
 * https://mp.weixin.qq.com/s/oS8r2DieTYBhFuyWF6jx9w
 */

var mBlock: (() -> Unit)? = null

inline fun testLambda(block1: () -> Unit, noinline block2: () -> Unit) {
    block1()
    mBlock = block2
    proxy(block2)
}

fun proxy(block: () -> Unit) {
    println("代理前--")
    block()
    println("代理后--")
}

fun run1() {
    testLambda({
        return
    }, {
        return@testLambda
    })
}
