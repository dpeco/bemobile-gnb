package com.dpeco.bemobilegnb.features.dashboard.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by dpeco
 * ConversionRate model with serialized names from service
 */
data class ApiConversionRate(
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("rate") val rate: Double
)
