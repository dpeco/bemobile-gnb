package com.dpeco.bemobilegnb.features.dashboard.repository.mappers

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiConversionRate

class GetConversionRatesMapper {

    fun parseConversionRates(apiConversionRates: List<ApiConversionRate>): List<ConversionRate> {
        val conversionRates: ArrayList<ConversionRate> = ArrayList()

        for (apiConversionRate in apiConversionRates) {
            val conversionRate = ConversionRate(
                apiConversionRate.from,
                apiConversionRate.to,
                apiConversionRate.rate
            )
            conversionRates.add(conversionRate)
        }

        return conversionRates
    }
}