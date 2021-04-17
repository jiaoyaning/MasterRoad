package com.jyn.java_kotlin.KotlinLearn.Collection

/*
 * kotlin集合基本API
 * https://blog.csdn.net/xuyankuanrong/article/details/77477976
 *
 * Kotlin——高级篇（五）：集合之常用操作符汇总
 * https://blog.csdn.net/u013212407/article/details/80909970
 */
fun main() {
    val lists = mutableListOf<Int>(1, 2, 3, 4)

    //重复
    print("repeat:")
    repeat(10) {
        print("$it ")
    }
    //遍历
    print("\nforEach:")
    lists.forEach {
        print("$it ")
    }

    print("\nfor in:")
    //不带indices方法也可以
    for (i in lists.indices) {
        print("$i ")
    }

    print("\nfor in until:")
    //until 使用 infix 关键字 可以省略方法调用的.个()
    for (i in 0 until lists.size) {
        print("$i ")
    }

    //根据条件进行过滤
    val filter = lists.filter { it > 2 }
    print("filter:")
    println(filter)

    val map = lists.map { it + 10 }.map { it.toString() }
    print("map:")
    println(map)
}