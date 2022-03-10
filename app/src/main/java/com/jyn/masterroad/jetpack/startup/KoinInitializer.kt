package com.jyn.masterroad.jetpack.startup

import android.content.Context
import androidx.startup.Initializer
import com.jyn.masterroad.kotlin.koin.appKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        //注意: startKoin只能执行一次，如果多次执行会抛出"A KoinContext is already started
        startKoin {
            //使用Koin Android Logger
            androidLogger(Level.INFO)
            //声明Android上下文
            androidContext(context)
            //声明要使用的模块
//            modules(appKoinModule)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}