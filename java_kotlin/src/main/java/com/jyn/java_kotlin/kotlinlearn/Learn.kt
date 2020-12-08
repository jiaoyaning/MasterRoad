package com.jyn.java_kotlin.kotlinlearn

/**
 * Created by jiaoyaning on 2020/12/8.
 */
class Learn {

}

fun main() {
    10.sayHollow()
    10.testAttr = " 猜猜我是几？ "
    println(10.testAttr)
}

/**
 * 扩展(静态解析，在编译期就已决定使用哪个方法)
 *
 * 在不改变原有类的基础上，添加成员属性，或者成员方法。
 * 如果扩展函数和原来的函数一样，会使用原先函数，不用担心会被覆盖
 */
fun Int.sayHollow() {
    println("$this say Hollow")
}

var Int.testAttr: String
    get() = "{$this}我多了一个成员属性"
    set(value) {
        println("我设置了一个成员属性${value}")
    }
