package com.macrosystems.sixtapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

  companion object{
    private const val API_BASE_URL = "https://cdn.sixt.io"

    private var carApi: CarApi? = null

    fun getRetrofit(): CarApi {
      return carApi ?: build()
    }

    private fun build(): CarApi {
      val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

      val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
      httpClient.addInterceptor(getHttpLoggingInterceptor())

      val retrofit: Retrofit = builder.client(httpClient.build()).build()

      carApi = retrofit.create(CarApi::class.java) as CarApi
      return carApi as CarApi
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      return httpLoggingInterceptor
    }
  }

}