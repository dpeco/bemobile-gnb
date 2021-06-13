package com.dpeco.bemobilegnb.features.dashboard.repository.entities

import com.google.gson.annotations.SerializedName

data class ApiConversionRate(
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("rate") val rate: Double
)
