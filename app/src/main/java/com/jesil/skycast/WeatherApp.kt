package com.jesil.skycast

import android.app.Application
import com.jesil.skycast.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin{
            androidContext(this@WeatherApp)
            androidLogger()
            modules(
                appModule
            )
        }
    }
}