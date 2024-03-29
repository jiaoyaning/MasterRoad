package com.jyn.language.Kotlin学习.集合and序列

/*
 * Kotlin——高级篇（五）：集合之常用操作符汇总
 * https://blog.csdn.net/u013212407/article/details/80909970
 *
 * Kotlin Collection VS Kotlin Sequence VS Java Stream
 * https://www.imooc.com/article/311591
 *
 * Kotlin 集合函数速查
 * https://mp.weixin.qq.com/s/xRTiSQZ3v_u0DQTwG0H04A
 *
 *
 * 注意：Kotlin中的集合扩展函数都是热流，如果要用冷流请使用 sequence
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