package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.di

import android.app.Application
import android.content.Context
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.UserApiService
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.UserInterface
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.UserRepository
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.networking.InternetConnectivity
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util.InternetConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        internetConnectivity: InternetConnectivity,
        userApiService: UserApiService,
    ): UserInterface {
        return UserRepository(internetConnectivity, userApiService)
    }

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideInternetConnectivity(context: Context): InternetConnectivity {
        return InternetConnectivityImpl(context)
    }
}