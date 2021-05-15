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
        startKoin {
            androidLogger(Level.INFO)
            androidContext(context)
            modules(appKoinModule)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}