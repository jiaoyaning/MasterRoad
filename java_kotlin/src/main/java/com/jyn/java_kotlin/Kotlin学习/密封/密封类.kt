package com.jyn.java_kotlin.Kotlin学习.密封

/*
 * Kotlin Sealed 是什么？为什么 Google 都在用 (优秀)
 * https://mp.weixin.qq.com/s/X1jEUFS8vmRF9ybkmVy5zQ
 */
sealed class Color {

    /*
     * 密封类可以约束子类的类型，相当于强化版的枚举，相对于枚举更加灵活：
     * Enum Class的限制
     *      枚举每个类型只允许有一个实例(甚至被推荐用来设置单例)
     *      所有枚举常量使用相同的类型的参数
     *
     *
     * Sealed 本质是一个 abstract 类，它本身是不能被实例化，只能用它的子类实例化对象。
     */


    object Red : Color() //object时，不是每次创建一个新实例，跟枚举功能重叠
    class Green(val value: Int) : Color() //class每次都会创建新实例
    class Blue(val name: String) : Color()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val red1 = Red
            val red2 = Red
            val green1 = Green(1)
            val green2 = Green(2)
            val blue1 = Blue("1")
            val blue2 = Blue("2")
            println(red1)
            println(red2)
            println(green1)
            println(green2)
            println(blue1)
            println(blue2)
        }
    }
}

