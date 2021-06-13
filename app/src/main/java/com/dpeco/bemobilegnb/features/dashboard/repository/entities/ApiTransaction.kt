package com.dpeco.bemobilegnb.features.dashboard.repository.entities

import com.google.gson.annotations.SerializedName

data class ApiTransaction(
    @SerializedName("sku") val sku: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
)
