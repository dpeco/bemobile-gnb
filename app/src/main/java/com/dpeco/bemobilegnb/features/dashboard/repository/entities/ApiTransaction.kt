package com.dpeco.bemobilegnb.features.dashboard.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by dpeco
 * Transaction model with serialized names from service
 */
data class ApiTransaction(
    @SerializedName("sku") val sku: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
)
