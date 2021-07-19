package com.macrosystems.sixtapp.data.network

import com.macrosystems.sixtapp.data.model.CarDetails
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

object Api {

  private const val API_BASE_URL = "https://cdn.sixt.io"
  private var servicesApiInterface: ServicesApiInterface? = null

  fun build(): ServicesApiInterface? {
    val builder: Retrofit.Builder = Retrofit.Builder()
      .baseUrl(API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())

    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    httpClient.addInterceptor(interceptor())

    val retrofit: Retrofit = builder.client(httpClient.build()).build()
    servicesApiInterface = retrofit.create(ServicesApiInterface::class.java)

    return servicesApiInterface as ServicesApiInterface
  }

  private fun interceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  interface ServicesApiInterface {
    @GET("/codingtask/cars")
    suspend fun cars(): Response<List<CarDetails>>
  }
}