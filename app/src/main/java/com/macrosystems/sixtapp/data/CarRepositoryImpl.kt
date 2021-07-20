package com.macrosystems.sixtapp.data


import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.network.Api
import com.macrosystems.sixtapp.data.network.CarDetailsSource
import com.macrosystems.sixtapp.data.network.Result
import java.lang.Exception

class CarRepositoryImpl : CarDetailsSource{

    override suspend fun getCarsDetails(): Result<CarDetails> {
        try {
            val response = Api.getRetrofit().getCars()
            response?.let {
                return if (it.isSuccessful && it.body() != null) {
                    Result.OnSuccess(it.body()!!)
                } else {
                    Result.OnError(Exception(it.message()))
                }
            } ?: run {
                return Result.OnError(Exception("Oops, we got an error, try again later please!"))
            }

        } catch (e: Exception){
            return Result.OnError(e)
        }
    }

}