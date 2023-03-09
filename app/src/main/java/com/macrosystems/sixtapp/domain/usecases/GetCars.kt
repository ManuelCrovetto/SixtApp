package com.macrosystems.sixtapp.domain.usecases

import com.macrosystems.sixtapp.data.model.CarData
import com.macrosystems.sixtapp.data.model.toDomain
import com.macrosystems.sixtapp.data.network.CarsService
import com.macrosystems.sixtapp.data.network.Result
import com.macrosystems.sixtapp.domain.model.CarDomain

class GetCars {

    private val carService = CarsService()

    suspend fun getCars(): List<CarDomain> {
        val result: Result<List<CarData>> = carService.getCars()
        return when(result) {
            is Result.OnError -> emptyList()
            is Result.OnSuccess -> {
                val list: List<CarDomain> = result.data.map { carData ->
                    carData.toDomain()
                }
                return list
            }
        }
    }
}