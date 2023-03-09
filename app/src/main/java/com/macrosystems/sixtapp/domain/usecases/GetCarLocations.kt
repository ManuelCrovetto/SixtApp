package com.macrosystems.sixtapp.domain.usecases

import com.macrosystems.sixtapp.data.model.CarData
import com.macrosystems.sixtapp.data.network.CarsService
import com.macrosystems.sixtapp.data.network.Result
import com.macrosystems.sixtapp.domain.model.CarLocation

class GetCarLocations {

    private val carsService = CarsService()

    suspend fun getCarLocations(): List<CarLocation> {
        val result: Result<List<CarData>> = carsService.getCars()
        return when(result) {
            is Result.OnError -> emptyList()
            is Result.OnSuccess -> {
                val carLocationList = result.data.map { carData ->
                    CarLocation(
                        licensePlate = carData.licensePlate ?: "hola k no ase",
                        longitude = carData.longitude.toString(),
                        latitude = carData.latitude.toString()
                    )
                }
                return carLocationList

            }
        }
    }
}