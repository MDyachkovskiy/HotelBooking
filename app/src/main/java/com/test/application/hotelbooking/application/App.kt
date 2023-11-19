package com.test.application.hotelbooking.application

import android.app.Application
import com.test.application.hotelbooking.di.interactorModule
import com.test.application.hotelbooking.di.networkModule
import com.test.application.hotelbooking.di.repositoryModule
import com.test.application.hotelbooking.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, interactorModule, viewModelModule))
        }
    }
}