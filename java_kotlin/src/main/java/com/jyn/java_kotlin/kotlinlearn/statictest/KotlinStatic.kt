package com.jyn.java_kotlin.kotlinlearn.statictest

/**
 * kotlin 静态
 * Created by jiaoyaning on 2020/12/6.
 */
fun main() {
    staticFun()
    println(KotlinStatic.staticObjectTest)
    println(KotlinStaticClass.StaticClassTestWithConst)
    println(KotlinStaticClass.StaticClassTest)

    KotlinStatic.staticObjectFun()
    KotlinStaticClass.staticFun()
}

//包内方法之静态方法
fun staticFun() {
    println("1.这是一个包内方法的静态方法！")
}

object KotlinStatic {
    //const只能修饰val，没有自定义的getter方法，如果进行拼接或者运算，被拼接的其他变量也要是宏变量，否则编译不通过
    const val staticObjectTest = "2.这是一个object的静态常量！"
    fun staticObjectFun() {
        println("这是一个object里面的静态方法！")
    }
}

class KotlinStaticClass {
    companion object {
        var StaticClassTest = "3.这是一个正常类里面的伴生静态对象，var，非const！"

        //只有val修饰才能被const修饰
        const val StaticClassTestWithConst = "3.这是一个正常类里面的伴生静态对象！val常量，被const修饰！"

        @JvmField
        var StaticClassTestWithJvmField = "4.这是一个正常类里面的伴生静态对象，非const，添加@JvmField注解！"

        fun staticFun() {
            println("这是一个class里面伴生对象的静态方法！")
        }

        @JvmStatic
        fun staticFunWithJvmStatic() {
            println("这是一个class里面伴生对象的静态方法，添加@JvmStatic注解！")
        }
    }
}