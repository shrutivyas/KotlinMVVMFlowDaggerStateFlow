package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://gorest.co.in/public/"

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                /*if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(StethoInterceptor())
                    addInterceptor(logger)
                }*/
//                addInterceptor(authInterceptor)
                callTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
            }
            .build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val builder = GsonBuilder().disableHtmlEscaping().setLenient().create()
        return GsonConverterFactory.create(builder)
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        factory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(factory)
            .client(client)
            .build()
    }

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()
}