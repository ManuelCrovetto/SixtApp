package com.macrosystems.sixtapp.data.model

import com.google.gson.annotations.SerializedName
import com.macrosystems.sixtapp.domain.model.CarDomain

data class CarData(
    @SerializedName("modelName") var modelName: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("make") var make: String?,
    @SerializedName("group") var group: String?,
    @SerializedName("color") var color: String?,
    @SerializedName("series") var series: String?,
    @SerializedName("fuelLevel") var fuelLevel: String?,
    @SerializedName("licensePlate") var licensePlate: String?,
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("innerCleanliness") var innerCleanliness: String?,
    @SerializedName("carImageUrl") var carImageUrl: String?,
    var isExpanded: Boolean = false
)

fun CarData.toDomain(): CarDomain {
    return CarDomain(
        modelName = modelName,
        name = name,
        make = make,
        group = group,
        color = color,
        series = series,
        fuelLevel = fuelLevel,
        licensePlate = licensePlate,
        latitude = latitude,
        longitude = longitude,
        innerCleanliness = innerCleanliness,
        carImageUrl = carImageUrl
    )
}
