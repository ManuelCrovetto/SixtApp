package com.macrosystems.sixtapp.data.model

import android.net.Uri
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CarDetails(
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