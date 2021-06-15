package com.dpeco.bemobilegnb.features.dashboard.usecases

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService

class GetConversionRatesUseCase {

    private val service = DashboardService()

    suspend operator fun invoke():List<ConversionRate> = service.getConversionRates()
}