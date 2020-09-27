package com.ddd4.synesthesia.beer.di.module

import android.app.Application
import android.content.Context
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import com.ddd4.synesthesia.beer.util.filter.FilterImpl
import com.ddd4.synesthesia.beer.util.filter.FilterSetting
import com.ddd4.synesthesia.beer.util.sort.SortImpl
import com.ddd4.synesthesia.beer.util.sort.SortSetting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferenceProvider {
        return SharedPreferenceProvider(application)
    }

    @Provides
    @Singleton
    fun provideSortSettings(
        preference: SharedPreferenceProvider,
        @ApplicationContext context: Context
    ): SortSetting = SortImpl(preference, context)

    @Provides
    @Singleton
    fun provideFilterSettings(
        preference: SharedPreferenceProvider,
        @ApplicationContext context: Context
    ): FilterSetting = FilterImpl(preference, context)

}