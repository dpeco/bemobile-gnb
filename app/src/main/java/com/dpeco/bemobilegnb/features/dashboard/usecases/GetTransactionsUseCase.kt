package com.dpeco.bemobilegnb.features.dashboard.usecases

import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService

class GetTransactionsUseCase {

    private val service = DashboardService()

    suspend operator fun invoke():List<Transaction> = service.getTransactions()

}