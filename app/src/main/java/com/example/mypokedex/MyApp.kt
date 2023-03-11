package com.example.mypokedex

import android.app.Application
import com.example.mypokedex.di.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
        }

        DataModule.load()
    }

}