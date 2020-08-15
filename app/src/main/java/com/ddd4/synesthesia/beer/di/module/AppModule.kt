package com.ddd4.synesthesia.beer.di.module

import android.app.Application
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application : Application) : SharedPreferenceProvider {
        return SharedPreferenceProvider(application)
    }
}