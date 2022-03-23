package com.jyn.language.设计模式.单例模式

/*
 * Kotlin下的5种单例模式
 * https://www.jianshu.com/p/5797b3d0ebd0
 */

//线程安全的懒汉式
class KotlinSingleton1 private constructor() {
    companion object {
        private var instance: KotlinSingleton1? = null
            get() {
                if (field == null) {
                    field = KotlinSingleton1()
                }
                return field
            }

        @JvmStatic
        @Synchronized
        fun get(): KotlinSingleton1 {
            return instance!!
        }
    }
}

//双重校验（Double Check) 不带参数版
class KotlinSingleton2 private constructor() {
    companion object {
        @JvmStatic
        val instance: KotlinSingleton2 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            KotlinSingleton2()
        }
    }
}

//双重校验 带参数版本
class KotlinSingleton3 private constructor(var pre: String) {
    companion object {
        @Volatile
        private var instance: KotlinSingleton3? = null

        @JvmStatic
        fun getInstance(pre: String): KotlinSingleton3 {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = KotlinSingleton3(pre)
                    }
                }
            }
            return instance!!
        }
    }
}

/**
 * 饿汉式单例
 */
object KotlinObjectSingle {
    fun test() {
    }
}