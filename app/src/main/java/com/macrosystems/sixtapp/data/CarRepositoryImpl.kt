package com.macrosystems.sixtapp.data


import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.network.CarApi
import com.macrosystems.sixtapp.data.network.CarRepository
import com.macrosystems.sixtapp.data.network.Result
import java.lang.Exception
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val carApi: CarApi) : CarRepository{

    override suspend fun getCarsDetails(): Result<CarDetails> {
        try {
            val response = carApi.getCars()
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