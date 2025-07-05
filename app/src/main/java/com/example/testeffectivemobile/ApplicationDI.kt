package com.example.testeffectivemobile

import android.app.Application
import com.example.testEffectiveMobile.feature.auth.di.authModule
import com.example.testEffectiveMobile.feature.courses.di.homeModule
import com.example.testeffectivemobile.feature.favorites.di.favoritesModule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class ApplicationDI : Application() {
    lateinit var mockServerManagerWrapper: MockServerManagerWrapper
    lateinit var baseUrl: String

    override fun onCreate() {
        super.onCreate()
        mockServerManagerWrapper = MockServerManagerWrapper(this)

        runBlocking {
            withContext(Dispatchers.IO) {
                mockServerManagerWrapper.startMockServer()
                baseUrl = mockServerManagerWrapper.getUrl()
            }

        }

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@ApplicationDI)
            modules(
                networkModule(baseUrl),
                authModule,
                homeModule,
                favoritesModule,
                module {
                    single { mockServerManagerWrapper }
                }
            )
        }
    }
}