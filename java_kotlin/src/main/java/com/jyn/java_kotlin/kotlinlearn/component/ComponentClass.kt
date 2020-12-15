package com.jyn.java_kotlin.kotlinlearn.component

/**
 * Component 解构高级用法
 *
 * https://www.jianshu.com/p/4d2c0ba889f4
 */

class ComponentClass(var option1: String, var option2: Int) {
    //operator : 重载
    operator fun component1() = option1
    operator fun component2() = option2
}

fun main() {
    val componentClass = ComponentClass("test", 0)

    val (testOption1, testOption2) = componentClass

    println("testOption1:$testOption1")
    println("testOption2L$testOption2")

    //解构可用于循环Map集合
    val map:Map<Int,String> = mapOf(1 to "111",2 to "222")
    for ((k,v) in map){
        println("$k ---- $v")
    }
}