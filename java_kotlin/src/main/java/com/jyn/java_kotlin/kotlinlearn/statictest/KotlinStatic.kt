package com.jyn.java_kotlin.kotlinlearn.statictest

/**
 * kotlin 静态
 * Created by jiaoyaning on 2020/12/6.
 */
fun main() {
    testFun()
    println(KotlinStaticObject.testValWithConst)
    println(KotlinStaticClass.testValWithConst)
    println(KotlinStaticClass.testVar)

    KotlinStaticObject.testFun()
    KotlinStaticClass.testFun()
}

/**
 * top-level(顶层函数) 之静态方法
 * 顶层函数会反编译成一个static静态函数
 */
fun testFun() {
    println("1.这是一个包内方法的静态方法！")
}

object KotlinStaticObject {
    //const只能修饰val，没有自定义的getter方法，如果进行拼接或者运算，被拼接的其他变量也要是宏变量，否则编译不通过
    const val testValWithConst = "2.这是一个object的静态常量！val常量，被const修饰！"

    @JvmStatic
    var testVarWithJvmStatic = "2.这是一个object的静态常量！var常量，非const！添加@JvmStatic！"

    fun testFun() {
        println("这是一个object里面的静态方法！")
    }

    @JvmStatic
    fun testFunWithJvmStatic() {
        println("这是一个object里面的静态方法！带JvmStatic")
    }
}

class KotlinStaticClass {
    /**
     * 在 “companion object” 中的公共函数必须用使用 @JvmStatic 注解才能暴露为静态方法。
     *
     * 在 companion object 中的公共、非 const 的属性 实际上为常量必须用 @JvmField 注解才能暴露为静态字段。
     * 如果没有这个注解，这些属性只能作为静态 Companion 字段中奇怪命名的 ‘getters’ 实例。
     * 而只使用 @JvmStatic 而不是 @JvmField 的话，会将奇怪命名的 ‘getters’ 移到类的静态方法中，但仍然是不正确的。
     *
     */
    companion object {
        var testVar = "3.这是一个正常类里面的伴生静态对象，var，非const！"

        //只有val修饰才能被const修饰
        const val testValWithConst = "3.这是一个正常类里面的伴生静态对象！val常量，被const修饰！"

        //Java调用下可以避免写Companion
        @JvmField
        var testVarWithJvmField = "4.这是一个正常类里面的伴生静态对象，非const，添加@JvmField注解！"


        //JvmName注解，可以设置一个调用名字
        @JvmStatic //不加JvmStatic 调用时需要Companion
        @get:JvmName("JvmNameTest")
        var testVarWithJvmName = "5.这是一个正常类里面的伴生静态对象，非const，添加@JvmName注解！"
            private set

        fun testFun() {
            println("这是一个class里面伴生对象的静态方法！")
        }

        //Java调用下可以避免写Companion
        @JvmStatic
        fun staticFunWithJvmStatic() {
            println("这是一个class里面伴生对象的静态方法，添加@JvmStatic注解！")
        }
    }
}