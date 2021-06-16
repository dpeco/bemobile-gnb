package com.dpeco.bemobilegnb.dagger

import com.dpeco.bemobilegnb.features.dashboard.repository.DashboardService
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetConversionRatesUseCase
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetTransactionsUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by dpeco
 * Module which would be used to provide every Use Case class
 */
@Module
class UseCaseModule {

    @Provides
    fun provideGetConversionRatesUseCase(service: DashboardService): GetConversionRatesUseCase {
        return GetConversionRatesUseCase(service)
    }

    @Provides
    fun provideGetTransactionsUseCase(service: DashboardService): GetTransactionsUseCase {
        return GetTransactionsUseCase(service)
    }
}