package com.jesil.skycast

import android.app.Application
import com.jesil.skycast.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@WeatherApp)
            androidLogger()
            modules(
                appModule
            )
        }
    }
}