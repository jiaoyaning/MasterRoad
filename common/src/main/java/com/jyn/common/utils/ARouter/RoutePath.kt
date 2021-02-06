package com.jyn.common.utils.ARouter

/**
 * Created by jiaoyaning on 2020/9/16.
 */
object RoutePath {

    /**
     * 主activity
     * [com.jyn.masterroad.MainActivity].
     */
    object Main {
        const val name = "MainActivity"
        const val path = "/app/MainActivity"
    }

    /**
     * handler测试
     * [com.jyn.masterroad.handler.HandlerActivity]
     */
    object Handle {
        const val name = "HandlerActivity"
        const val path = "/app/HandlerActivity"
    }

    /**
     * LiveData测试
     * [com.jyn.masterroad.jetpack.livedata.LiveDataActivity]
     */
    object LiveData {
        const val name = "LiveDataActivity"
        const val path = "/app/LiveDataActivity"
    }

    /**
     * RxJava 测试
     * [com.jyn.masterroad.RxJava.RxJavaActivity]
     */
    object RxJava {
        const val name = "RxJavaActivity"
        const val path = "/app/RxJavaActivity"
    }

    /**
     * BindingCollection 双向绑定 recyclerview
     * [com.jyn.masterroad.jetpack.bindingcollection.BindingCollectionActivity]
     */
    object BindingCollection {
        const val name = "BindingCollectionAdapter"
        const val path = "/app/BindingCollectionActivity"
    }

    /**
     * ConstraintLayout 约束布局
     * [com.jyn.masterroad.ConstraintLayout.ConstraintLayoutActivity]
     */
    object ConstraintLayout{
        const val name = "ConstraintLayout"
        const val path = "/app/ConstraintLayout"
    }

    /**
     * MotionLayout 动画
     * [com.jyn.masterroad.ConstraintLayout.MotionLayoutActivity]
     */
    object  MotionLayout{
        const val name = " MotionLayout"
        const val path = "/app/ MotionLayout"
    }

    /**
     * NestedScrolling 嵌套联动布局
     * [com.jyn.masterroad.NestedScrolling.NestedScrolling1Activity]
     */
    object  NestedScrolling{
        const val name = " NestedScrolling1"
        const val path = "/app/ NestedScrolling1"
    }

    /**
     * Thread 线程
     */
    object Thread{
        const val name = "Thread"
        const val path = "app/Thread"
    }
}