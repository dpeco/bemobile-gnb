package com.dpeco.bemobilegnb.features.dashboard.app.model

import java.io.Serializable

/**
 * Created by dpeco
 */
data class TransactionMovement(
    val amount: Double,
    val currency: String
): Serializable
