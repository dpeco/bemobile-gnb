package com.dpeco.bemobilegnb.features.dashboard.usecases

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService

class GetConversionRatesUseCase {

    private val service = DashboardService()
    private val mapper = GetConversionRatesMapper()

    suspend operator fun invoke():ArrayList<ConversionRate> = mapper.parseConversionRates(service.getConversionRates())
}