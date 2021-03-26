package com.jyn.common.ARouter

/**
 * Created by jiaoyaning on 2020/9/16.
 */
object RoutePath {

    // 主activity
    object Main {
        const val name = "MainActivity"
        const val path = "/app/MainActivity"
    }

    // handler测试
    object Handle {
        const val name = "HandlerActivity"
        const val path = "/app/HandlerActivity"
    }

    // LiveData测试
    object LiveData {
        const val name = "LiveDataActivity"
        const val path = "/app/LiveDataActivity"
    }

    // RxJava 测试
    object RxJava {
        const val name = "RxJavaActivity"
        const val path = "/app/RxJavaActivity"
    }

    // BindingCollection 双向绑定 recyclerview
    object BindingCollection {
        const val name = "BindingCollectionAdapter"
        const val path = "/app/BindingCollectionActivity"
    }

    // ConstraintLayout 约束布局
    object ConstraintLayout {
        const val name = "ConstraintLayout"
        const val path = "/app/ConstraintLayout"
    }

    // MotionLayout 动画
    object MotionLayout {
        const val name = " MotionLayout"
        const val path = "/app/ MotionLayout"
    }

    // NestedScrolling 嵌套联动布局
    object NestedScrolling {
        const val name = " NestedScrolling1"
        const val path = "/app/ NestedScrolling1"
    }

    object NestedScrolling2 {
        const val name = " NestedScrolling2"
        const val path = "/app/ NestedScrolling2"
    }

    // Thread 线程
    object Thread {
        const val name = "Thread"
        const val path = "/app/Thread"
    }

    // Kotlin Coroutines协程
    object KotlinCoroutines {
        const val name = "Kotlin_Coroutines"
        const val path = "/app/KotlinCoroutines"
    }
}