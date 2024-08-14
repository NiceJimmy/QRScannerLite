package com.deviz.qrscannerlite

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Timber 초기화
            Timber.plant(Timber.DebugTree())
    }
}

