package com.lilcode.aop.p5c01.todo

import android.app.Application
import com.lilcode.aop.p5c01.todo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AopPart5Chapter01Application : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO Koin Trigger
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AopPart5Chapter01Application)
            modules(appModule)
        }

    }
}