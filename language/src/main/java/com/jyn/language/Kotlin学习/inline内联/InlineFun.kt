package com.jyn.language.Kotlin学习.inline内联

/*
 * inline、noinline、crossinline傻傻分不清楚
 * https://mp.weixin.qq.com/s/qpxdSoyFB2vFs6iZpXPsHg
 *
 * Kotlin源码里成吨的noinline和crossinline是干嘛的
 * https://zhuanlan.zhihu.com/p/224965169
 *
 * inline 修饰的高阶函数中的 Lambda 才可以使用 return
 * https://mp.weixin.qq.com/s/LM5ihyo0OCzBk-AgWXVuUQ
 *
 * 重学 Kotlin —— inline，包治百病的性能良药？
 * https://mp.weixin.qq.com/s/3TQQrl01F6jamMXWjK3f7w
 */
class InlineFun {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            /*
             * 高阶函数普通应用时，会创建临时Function接口对象，多次调用会加大内存开销
             */
            funTest { println("funTest after") }
            println()

            /*
             * inline 内联，把原本的代码内联到所调用的地方，减少临时变量的创建
             * Kotlin语言中，Lambda表达式里不允许使用return，除非整个Lambda表达式是内联函数的参数。「--最下方测试」
             */
            inlineTest { println("inlineTest after") }
            println()

            /*
             * inline 作用于函数，noinline作用于函数类型参数。
             * inline 代表整个函数内联，noinline则代表该函数类型参数不参与内联。
             */
            noinlineTest({
                println("noinlineTest before")
            }, {
                println("noinlineTest after")
                return@noinlineTest
            })
            println()

            /*
             * noinline 解决的是内联函数中的函数类型参数无法当作对象操作的问题
             * crossinline 则是解决内联函数中函数类型参数无法被间接调用以及非局部返回的问题。
             */
            crossinlineTest({
                println("crossinlineTest before")
            }, {
                println("crossinlineTest after")
                return@crossinlineTest
            })
            println()

            /*
             * Kotlin语言中，Lambda表达式里不允许使用return方式结束main方法
             * （并不代表着没办法使用return关键字，如上所示可以加上@方法名表示只结束本方法），除非整个Lambda表达式是内联函数的参数。
             *
             * 由于函数内联优化，两个函数类型参数均被内联，代码铺平后，return直接结束的是main方法，
             */
            inlineTest {
                println("inlineTest after")
                return //此处 return 代表外层方法栈，跟上面的return@crossinlineTest作用域完全不同
            }
            println("inlineTest return后 是否还可继续执行") //并不会继续执行
        }

        private fun funTest(after: () -> Unit) {
            println("funTest")
            after()
        }

        private inline fun inlineTest(after: () -> Unit) {
            println("inlineTest")
            after()
        }

        /*
         * 未使用内联修饰符时：函数类型参数可作为对象进行使用，我们可以将其赋值给变量，
         *              可以将其作为参数传递给另外的函数，也可以将其作为返回值传递出去；
         *
         * 使用内联修饰符后：代码被铺平，临时对象也就不存在了，所以赋值给变量、作为参数传递给另外的函数以及以返回值的方式传递出去均无法实现。
         */
        private inline fun noinlineTest(before: () -> Unit, noinline after: () -> Unit) {
            before()
            println("noinline")
            warpAfter(after)
        }

        /*
         * 内联函数里函数类型参数不允许间接调用，通过crossinline修饰符允许函数类型参数被间接调用。
         *
         * 声明一个 lambda 不能有 return 语句(可以有 return@label 语句)。这样可以避免使用 inline 时，lambda 中的 return 影响程序流程。
         */
        private inline fun crossinlineTest(before: () -> Unit, crossinline after: () -> Unit) {
            before()
            println("crossinline")
            warpAfter { after() }
        }

        private fun warpAfter(warp: () -> Unit) {
            warp()
            println("warpAfter")
        }
    }
}