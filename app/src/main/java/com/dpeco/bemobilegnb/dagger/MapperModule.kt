package com.dpeco.bemobilegnb.dagger

import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetConversionRatesMapper
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetTransactionsMapper
import dagger.Module
import dagger.Provides

/**
 * Created by dpeco
 * Module which would be used to provide every Mapper class
 */
@Module
class MapperModule {

    @Provides
    fun provideGetConversionRatesMapper(): GetConversionRatesMapper {
        return GetConversionRatesMapper()
    }

    @Provides
    fun provideGetTransactionsMapper(): GetTransactionsMapper {
        return GetTransactionsMapper()
    }
}