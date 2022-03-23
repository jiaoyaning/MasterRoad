package com.jyn.language.Kotlin学习.component解构方法

/**
 * Component 解构高级用法
 * https://www.jianshu.com/p/4d2c0ba889f4
 */

//第一种： 重写类的component方法
class ComponentClass(var option1: String, var option2: Int) {
    //operator : 重载
    operator fun component1() = option1
    operator fun component2() = option2
}

//第二种：添加data关键字，可以把data的属性，全部剥离出来单独使用
data class ComponentClass2(var option1: String, var option2: Int)

fun main() {
    val componentClass = ComponentClass("test", 0)
    val componentClass2 = ComponentClass2("test2", 100)

    val (testOption1, testOption2) = componentClass
    val (testOption3, testOption4) = componentClass2

    println("testOption1:$testOption1")
    println("testOption2:$testOption2")
    println("testOption3:$testOption3")
    println("testOption4:$testOption4")

    //解构可用于循环Map集合
    val map: Map<Int, String> = mapOf(1 to "111", 2 to "222")
    for ((k, v) in map) {
        println("$k ---- $v")
    }
}