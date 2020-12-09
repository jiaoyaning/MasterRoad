package com.jyn.java_kotlin.kotlinlearn

/**
 * Created by jiaoyaning on 2020/12/8.
 */
fun main() {
    println("============扩展特性=============")
    10.sayHollow()
    10.testAttr = " 猜猜我是几？ "
    println(10.testAttr)

    println("============方法类型=============")
    functionTypeTest1 { println("这是一个无参无返回值的函数类型 ") }//大括号表示这是一个方法
    functionTypeTest1(Learn()::fun1) //方法调用，要用小括号

    functionTypeTest2 { println("这是一个有参无返回值的内部函数类型 int -> $it") }
    functionTypeTest2(::intFun)
}

fun intFun(int: Int) {
    println("这是一个有参无返回值的外部函数类型 int -> $int")
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

//函数类型
fun functionTypeTest1(fun1: () -> Unit) {
    fun1()
}

fun functionTypeTest2(fun1: (intTest: (Int)) -> Unit) {
    fun1(10)
}

class Learn {
    fun fun1() {
        println("这是一个无参无返回值的方法")
    }
}