package com.ddd4.synesthesia.beer.di.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.ddd4.synesthesia.beer.BuildConfig
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.remote.service.BeerApi
import com.ddd4.synesthesia.beer.data.source.remote.service.KakaoApi
import com.ddd4.synesthesia.beer.data.source.remote.service.KakaoAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOAuthOkHttpClient(application: Application) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor.invoke {
                it.run {
                    val request = request().newBuilder()
                    application.getSharedPreferences("BEER",MODE_PRIVATE).getString("token","")?.let {
                        request.addHeader("Authorization","Bearer " + application.getSharedPreferences("BEER",MODE_PRIVATE).getString("token","")!!)
                    }
                    proceed(request.build())
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                } })
            .build()
    }
    @Provides
    @Singleton
    @Named("kakaoAuth")
    fun provideKakaoAuthRetrofit(application: Application) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kauth.kakao.com")
            .client(provideOAuthOkHttpClient(application))
            .build()
    }

    @Provides
    @Singleton
    @Named("kakao")
    fun provideKakaoRetrofit(application: Application) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kapi.kakao.com")
            .client(provideOAuthOkHttpClient(application))
            .build()
    }
    @Provides
    @Singleton
    fun provideKakaoAuthService(@Named("kakaoAuth") retrofit: Retrofit) : KakaoAuthApi {
        return retrofit.create(KakaoAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoService(@Named("kakao") retrofit: Retrofit) : KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOtherOkHttpClient(application: Application) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor.invoke {
                it.run {
                    val request = request().newBuilder()
                        .addHeader("Authorization",application.getSharedPreferences("BEER",MODE_PRIVATE).getString("token","")!!)
                        .build()
                    proceed(request)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                } })
            .build()
    }

    @Provides
    @Singleton
    @Named("beer")
    fun provideRetrofit(application : Application) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(application.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOtherOkHttpClient(application))
            .build()
    }

    @Provides
    @Singleton
    fun provideBeerService(@Named("beer") retrofit: Retrofit) : BeerApi {
        return retrofit.create(BeerApi::class.java)
    }




}