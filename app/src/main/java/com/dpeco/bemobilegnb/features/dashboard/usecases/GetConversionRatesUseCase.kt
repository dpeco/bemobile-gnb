package com.dpeco.bemobilegnb.features.dashboard.usecases

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService
import java.util.*

/**
 * Created by dpeco
 * UseCase to get Conversion Rates from Dashboard service / repository
 */
class GetConversionRatesUseCase(val service: DashboardService) {
    suspend operator fun invoke():List<ConversionRate> =  service.getConversionRates()
}