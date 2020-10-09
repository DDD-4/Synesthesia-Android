package com.ddd4.synesthesia.beer.di.module

import com.ddd4.synesthesia.beer.data.repository.BeerRepositoryImpl
import com.ddd4.synesthesia.beer.data.repository.LoginRepositoryImpl
import com.ddd4.synesthesia.beer.data.source.remote.service.BeerApi
import com.ddd4.synesthesia.beer.data.source.remote.service.KakaoApi
import com.ddd4.synesthesia.beer.data.source.remote.service.KakaoAuthApi
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(kakaoApi : KakaoApi, kakaoAuthApi : KakaoAuthApi, preference : SharedPreferenceProvider) : LoginRepository {
        return LoginRepositoryImpl(kakaoApi,kakaoAuthApi,preference)
    }

    @Provides
    @Singleton
    fun provideBeerRepository(beerApi : BeerApi) : BeerRepository {
        return BeerRepositoryImpl(beerApi)
    }
}