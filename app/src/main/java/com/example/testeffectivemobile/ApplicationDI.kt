package com.example.testeffectivemobile

import android.app.Application
import org.koin.core.context.startKoin

class ApplicationDI : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
            )
        }
    }
}