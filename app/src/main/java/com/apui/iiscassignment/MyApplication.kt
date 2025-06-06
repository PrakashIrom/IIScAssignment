package com.apui.iiscassignment

import android.app.Application
import com.apui.iiscassignment.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(authModule)
        }
    }
}