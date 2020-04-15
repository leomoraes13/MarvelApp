package com.leomoraes.marvelapp

import android.app.Application
import com.leomoraes.marvelapp.di.imageModule
import com.leomoraes.marvelapp.di.repositoryModule
import com.leomoraes.marvelapp.di.serviceModule
import com.leomoraes.marvelapp.di.viewModelModule
import org.koin.core.context.startKoin

class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            modules(
                listOf(
                    serviceModule,
                    repositoryModule,
                    viewModelModule,
                    imageModule
                )
            )
        }
    }
}