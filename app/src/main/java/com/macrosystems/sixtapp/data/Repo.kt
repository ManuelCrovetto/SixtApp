package com.macrosystems.sixtapp.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.network.Api
import com.macrosystems.sixtapp.data.network.CarDetailsSource
import com.macrosystems.sixtapp.data.network.Result
import java.lang.Exception

class Repo : CarDetailsSource{

    /*fun getCarDetails(): LiveData<MutableList<CarDetails>>{
        val list = MutableLiveData<MutableList<CarDetails>>()
        db.getCarDetails().observeForever {
            list.value = it
        }
        return list
    }*/

    override suspend fun getCarsDetails(): Result<CarDetails> {
        try {
            val response = Api.build()?.cars()
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