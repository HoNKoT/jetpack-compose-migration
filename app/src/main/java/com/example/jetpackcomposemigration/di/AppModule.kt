package com.example.jetpackcomposemigration.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.jetpackcomposemigration.models.local.OrmaDatabase
import com.github.gfx.android.orma.AccessThreadConstraint
import com.github.gfx.android.orma.migration.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ClientModule::class
    ]
)
class AppModule(app: Application) {
    private val context: Context

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideResources(): Resources {
        return context.resources
    }

    @Singleton
    @Provides
    fun provideOrmaDatabase(context: Context): OrmaDatabase {
        return getOrmaInstance(context)!!
    }

//    @Singleton
//    @Provides
//    fun provideRetrofit(requestInterceptor: RequestInterceptor): Retrofit {
//        val httpClient = HttpClientCreator.create(requestInterceptor)
//        val gson = provideGson()
//        val apiBase = AppConfig.getInstance().API_SCHEME + "://" + AppConfig.getInstance().API_DOMAIN
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .baseUrl(apiBase)
//            .client(httpClient.build())
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideSenriService(retrofit: Retrofit): CustomA {
//        return retrofit.create(SenriService::class.java)
//    }

    private fun provideGson(): Gson {
        return getGson()
    }

    companion object {
        private const val DATABASE_NAME = "tsuru.db"

        private var orma: OrmaDatabase? = null

        var appContext: Context? = null

        @JvmStatic
        fun getOrmaInstance(context: Context): OrmaDatabase? {
            if (orma == null) {
                orma = OrmaDatabase.builder(context)
                    .readOnMainThread(AccessThreadConstraint.NONE)
                    .writeOnMainThread(if (BuildConfig.DEBUG) AccessThreadConstraint.WARNING else AccessThreadConstraint.NONE)
                    .name(DATABASE_NAME)
                    .build()
            }
            return orma
        }

        @JvmStatic
        fun getGson(): Gson {
            return GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        }
    }

    init {
        context = app
        appContext = app
    }
}
