package com.jyn.java_kotlin.Kotlin学习

class IsNull {
    // 非空类型，不能设置为null
    var a: String = "Test"

    // 可空类型，可设置为null
    var b: String? = "1"
    var c: Int? = null
    var d: Long? = null
    var length: Int? = 0

    fun test() {
        // 安全调用操作符(?)
        length = b?.length

        // 非空断言运算符(!!)，当觉得某个对象一定不为null时可用，但对象为null还是会npe
        length = b!!.length

        // 类型转换(as)，空返回NPE，转换失败返回ClassCastException
        c = b as Int

        // 安全类型转换(as?)，转换失败会返回null，不会抛异常
        c = b as? Int

        // Elvis操作符(?:)，三目运算符简略写法，如果不为空返回它，否则返回另一个值
        a = b ?: ""
    }
}