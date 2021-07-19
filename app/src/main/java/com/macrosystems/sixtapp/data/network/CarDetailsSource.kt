package com.macrosystems.sixtapp.data.network

import com.macrosystems.sixtapp.data.model.CarDetails

interface CarDetailsSource {
    suspend fun getCarsDetails(): Result<CarDetails>
}