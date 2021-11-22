package com.example.jetpackcomposemigration.di

import com.example.jetpackcomposemigration.CustomApplication
import com.example.jetpackcomposemigration.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(app: CustomApplication)

    fun inject(fragment: MainFragment)
}
