package com.dpeco.bemobilegnb.features.dashboard.app.model

import java.io.Serializable

data class TransactionMovement(
    val amount: Double,
    val currency: String
): Serializable
