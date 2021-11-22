package com.example.jetpackcomposemigration

import android.app.Application
import com.example.jetpackcomposemigration.di.AppComponent
import com.example.jetpackcomposemigration.di.AppModule
import com.example.jetpackcomposemigration.di.DaggerAppComponent

class CustomApplication : Application() {
    var appComponent: AppComponent? = null

    val component: AppComponent
        get() = appComponent!!

    override fun onCreate() {
        super.onCreate()
        setUp()
    }

    open fun setUp() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
