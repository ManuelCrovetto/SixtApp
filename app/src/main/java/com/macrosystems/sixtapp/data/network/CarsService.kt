package com.macrosystems.sixtapp.data.network

import com.macrosystems.sixtapp.data.model.CarData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarsService {

    private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://cdn.sixt.io").build().create(CarApi::class.java)

    suspend fun getCars(): Result<List<CarData>> {
        return try {
            val response = retrofit.getCars()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.OnSuccess(body)
                } ?: run {
                    Result.OnError(Exception("Body is null"))
                }

            } else {
                Result.OnError(Exception("No satisfactoria respuesta"))
            }
        } catch (e: Exception) {
            Result.OnError(Exception("Crasheo internet loco"))
        }
    }
}