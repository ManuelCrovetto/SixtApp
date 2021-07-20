package com.macrosystems.sixtapp.di

import com.macrosystems.sixtapp.data.network.CarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val API_BASE_URL = "https://cdn.sixt.io"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(getHttpLoggingInterceptor())

        return builder.client(httpClient.build()).build()
    }

    @Singleton
    @Provides
    fun provideCarApi(retrofit: Retrofit): CarApi {
        return retrofit.create(CarApi::class.java)
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}