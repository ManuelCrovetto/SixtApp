package com.macrosystems.sixtapp.data.network

import com.macrosystems.sixtapp.data.model.CarDetails
import retrofit2.Response
import retrofit2.http.GET

interface CarApi {
    @GET("/codingtask/cars")
    suspend fun getCars(): Response<List<CarDetails>>?
}