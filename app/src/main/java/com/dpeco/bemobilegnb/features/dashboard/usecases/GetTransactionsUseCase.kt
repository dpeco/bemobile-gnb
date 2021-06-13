package com.dpeco.bemobilegnb.features.dashboard.usecases

import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService

class GetTransactionsUseCase {

    private val service = DashboardService()
    private val mapper = GetTransactionsMapper()

    suspend operator fun invoke():ArrayList<Transaction> = mapper.parseTransactions(service.getTransactions())

}