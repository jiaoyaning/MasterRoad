package com.jyn.java_kotlin.Kotlin学习.属性委托

import java.lang.Thread.sleep
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 一文彻底搞懂Kotlin中的委托
 * https://mp.weixin.qq.com/s/dYBYuBAnYRgSiKQrU-ZGpg
 *
 * Kotlin 基础 | 委托及其应用
 * https://mp.weixin.qq.com/s/Oi4zX3AExZLp8D5WnWvlZg
 */
fun main(args: Array<String>) {
    propertyTest()
    println("\n===========分割线==============\n")
    lazyTest()
    println("\n===========分割线==============\n")
    observableTest()
    println("\n===========分割线==============\n")
    vetoableTest()
    println("\n===========分割线==============\n")
    test()
}

//region 1. 自定义委托测试
fun propertyTest() {
    // 1.测试自定义的属性委托
    var prop: String by Delegate()
    prop = "我是测试Value"
    println("打印结果:${prop}")

    println("===========分割线==============")

    // 2.测试自读属性委托
    val readOnlyProp: String by ReadOnlyDelegate()
    println("打印结果:${readOnlyProp}")

    println("===========分割线==============")

    // 3.测试可读写属性委托
    var readWriteProp: String by ReadWriteDelegate()
    readWriteProp = "我是测试可读写Value"
    println("打印结果:${readWriteProp}")
}

//region 1.1 自定义属性委托类
class Delegate {
    var value: String = "我是默认Value"

    /*
     * thisRef:     属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型；
     * property:    属性 必须是类型 KProperty<*>或其超类型。
     * value:       必须与属性同类型或者是它的子类型。
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("getValue被调用 --> thisRef:$thisRef ; property.name:${property.name} ")
        return this.value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("setValue被调用 --> thisRef:$thisRef ; property.name:${property.name} ; value:$value")
        this.value = value
    }
}
//endregion

//region 1.2. ReadOnlyProperty 只读
class ReadOnlyDelegate : ReadOnlyProperty<Any?, String> {
    var value: String = "我是默认只读Value"

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("getValue被调用 --> thisRef:$thisRef ; property.name:${property.name} ")
        return value
    }
}

//endregion

//region 1.3. ReadWriteProperty 可读写
class ReadWriteDelegate : ReadWriteProperty<Any?, String> {
    var value: String = "我是默认可读写的Value"
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("getValue被调用 --> thisRef:$thisRef ; property.name:${property.name} ")
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("setValue被调用 --> thisRef:$thisRef ; property.name:${property.name} ; value:$value")
        this.value = value
    }
}
//endregion
//endregion

//region 2. lazy 延迟属性
val lazyProp: String by lazy {
    println("--> 这是一个延迟初始化属性测试！")
    "延迟初始化的value"
}

/*
 * SYNCHRONIZED:添加同步锁，使lazy延迟初始化线程安全
 * PUBLICATION: 初始化的lambda表达式可以在同一时间被多次调用，但是只有第一个返回的值作为初始化的值。
 * NONE:        没有同步锁，多线程访问时候，初始化的值是未知的，非线程安全，一般情况下，不推荐使用这种方式，除非你能保证初始化和属性始终在同一个线程
 */
var i = 0
val lazyProp2: Int by lazy(LazyThreadSafetyMode.NONE) { i++ }

fun lazyTest() {
    println(lazyProp)
    println(lazyProp)

    println("===========分割线==============")

    (0..100).forEach { _ ->
        Thread {
            sleep(100)
            print(lazyProp2)
        }.start()
    }
    sleep(1000)
    println("\n$lazyProp2")
}
//endregion

//region 3. observable 可观察属性变化
var observableProp: String by Delegates.observable("默认Value") { property, oldValue, newValue ->
    println("property: $property ; oldValue{$oldValue} -> newValue{$newValue}")
}

fun observableTest() {
    observableProp = "第一次修改"
    observableProp = "第二次修改"
}

//endregion

//region 4. vetoable 处理器函数来决定属性值是否生效
var vetoableProp: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
    println("property: $property ; oldValue{$oldValue} -> newValue{$newValue}")
    // 如果新的值大于旧值，则生效
    newValue > oldValue
}

fun vetoableTest() {
    vetoableProp = -1
    println(vetoableProp)
    vetoableProp = 50
    println(vetoableProp)
    vetoableProp = 30
    println(vetoableProp)
    vetoableProp = 70
}

//endregion

//region 5. 属性存储在映射中
class Test(val map: Map<String, Any?>) {
    val test1: String by map
    val test2: Int by map
}

fun test() {
    val user = Test(mapOf("test1" to "这是Test1的Value", "test2" to 25))
    println("test1=${user.test1} ; test2=${user.test2}")
}

//endregion

