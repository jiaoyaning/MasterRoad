package com.jyn.common.ARouter

/**
 * Created by jiaoyaning on 2020/9/16.
 */
object RoutePath {

    object Fragment {
        const val name = "Fragment"
        const val path = "/app/Fragment"
        const val span = 1
    }

    object IpcClient {
        const val name = "IpcClient"
        const val path = "/app/IpcClientActivity"
        const val span = 2
    }

    object IpcServer {
        const val name = "IpcServer"
        const val path = "/app/IpcServerActivity"
        const val span = 2
    }

    // handler测试
    object Handle {
        const val name = "Handler"
        const val path = "/app/HandlerActivity"
        const val span = 1
    }

    // LiveData测试
    object LiveData {
        const val name = "LiveData"
        const val path = "/app/LiveDataActivity"
        const val span = 2
    }

    // Hilt测试
    object Hilt {
        const val name = "Hilt"
        const val path = "/app/Hilt"
        const val span = 2
    }

    // RxJava 测试
    object RxJava {
        const val name = "RxJava"
        const val path = "/app/RxJavaActivity"
        const val span = 2
    }

    object OkHttp {
        const val name = "OkHttp"
        const val path = "/app/OkhttpActivity"
        const val span = 2
    }

    object Okio {
        const val name = "Okio"
        const val path = "/app/OkioActivity"
        const val span = 2
    }

    object Retrofit {
        const val name = "Retrofit"
        const val path = "/app/Retrofit"
        const val span = 2
    }

    // BindingAdapter 双向绑定 recyclerview
    object BindingAdapter {
        const val name = "BindingAdapter"
        const val path = "/app/BindingAdapterActivity"
        const val span = 2
    }

    // ConstraintLayout 约束布局
    object ConstraintLayout {
        const val name = "ConstraintLayout"
        const val path = "/app/ConstraintLayout"
        const val span = 2
    }

    // MotionLayout 动画
    object MotionLayout {
        const val name = " MotionLayout"
        const val path = "/app/ MotionLayout"
        const val span = 2
    }

    // NestedScrolling 嵌套联动布局
    object NestedScrolling {
        const val name = " NestedScrolling1"
        const val path = "/app/ NestedScrolling1"
        const val span = 2
    }

    object NestedScrolling2 {
        const val name = " NestedScrolling2"
        const val path = "/app/ NestedScrolling2"
        const val span = 2
    }

    object RecyclerView {
        const val name = " RecyclerView"
        const val path = "/app/ RecyclerView"
        const val span = 2
    }

    // Thread 线程
    object Thread {
        const val name = "Thread"
        const val path = "/app/Thread"
        const val span = 1
    }

    // Kotlin Coroutines协程
    object KotlinCoroutines {
        const val name = "Kotlin -> Coroutines"
        const val path = "/app/KotlinCoroutines"
        const val span = 2
    }

    object Flow {
        const val name = "Kotlin -> Flow"
        const val path = "/app/Flow"
        const val span = 2
    }

    object KotlinKoin {
        const val name = "Kotlin -> Koin"
        const val path = "/app/Koin"
        const val span = 2
    }

    object ShapeableImageView {
        const val name = "ShapeableImageView"
        const val path = "/app/ShapeableImageView"
        const val span = 2
    }

    object MaterialButton {
        const val name = "MaterialButton"
        const val path = "/app/MaterialButton"
        const val span = 2
    }

    object LeakCanary {
        const val name = "LeakCanary"
        const val path = "/app/LeakCanary"
        const val span = 2
    }
}