package com.dpeco.bemobilegnb.features.dashboard.app.model

import java.io.Serializable

/**
 * Created by dpeco
 */
data class Transaction(
    val sku: String,
    val movements: ArrayList<TransactionMovement>,
    var totalAmountInEuro: Double = 0.0
): Serializable
