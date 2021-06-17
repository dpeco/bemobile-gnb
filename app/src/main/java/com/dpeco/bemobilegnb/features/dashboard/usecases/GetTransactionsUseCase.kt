package com.dpeco.bemobilegnb.features.dashboard.usecases

import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService

/**
 * Created by dpeco
 * UseCase to get Transactions from Dashboard service / repository
 */
class GetTransactionsUseCase(val service: DashboardService) {
    suspend operator fun invoke():List<Transaction> = service.getTransactions()
}