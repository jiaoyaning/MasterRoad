package com.jyn.masterroad

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

/*
 * Android 重学系列 Binder的总结
 * https://www.jianshu.com/p/62eee6cf03a3
 */
class BinderTestService : Service() {

    private val binder = ServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    /**
     * inner:
     * kotlin中所有的内部类默认为静态的，这样很好的减少了内存泄漏问题。
     * 如果需要在内部类引用外部类的对象，可以使用inner声明内部类，使内部类变为非静态的，通过this@外部类名，指向外部类。
     */
    inner class ServiceBinder : Binder() {
        //暴露Service，以便外部调用
        fun getService(): BinderTestService = this@BinderTestService
    }

    val randomNumber = Random().nextInt(1000)
}
