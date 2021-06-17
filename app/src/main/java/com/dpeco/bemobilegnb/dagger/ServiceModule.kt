package com.dpeco.bemobilegnb.dagger

import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetConversionRatesMapper
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetTransactionsMapper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by dpeco
 * Module which would be used to provide every service class
 */
@Module
class ServiceModule {

    @Provides
    fun provideDashboardService(retrofit: Retrofit, conversionRatesMapper: GetConversionRatesMapper, transactionsMapper: GetTransactionsMapper): DashboardService {
        return DashboardService(retrofit, conversionRatesMapper, transactionsMapper)
    }
}