package com.dpeco.bemobilegnb.features.dashboard.repository

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetConversionRatesMapper
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetTransactionsMapper
import com.dpeco.bemobilegnb.retrofit.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DashboardService {

    suspend fun getConversionRates(): List<ConversionRate> {
        return withContext(Dispatchers.IO) {
            val mapper = GetConversionRatesMapper()
            try {
                val call = RetrofitProvider.provideRetrofit().create(DashboardRepository::class.java).getConversionRates()
                call.body()?.let { mapper.parseConversionRates(it) } ?: emptyList()
            } catch (e:Exception) {
                emptyList()
            }
        }
    }
    suspend fun getTransactions(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            val mapper = GetTransactionsMapper()
            try {
                val call = RetrofitProvider.provideRetrofit().create(DashboardRepository::class.java).getTransactions()
                call.body()?.let { mapper.parseTransactions(it) } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}