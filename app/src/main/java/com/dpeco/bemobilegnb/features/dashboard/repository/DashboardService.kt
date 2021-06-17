package com.dpeco.bemobilegnb.features.dashboard.repository

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetConversionRatesMapper
import com.dpeco.bemobilegnb.features.dashboard.repository.mappers.GetTransactionsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.lang.Exception

/**
 * Created by dpeco
 * Logic to call the methods from DashboardRepository in a IO thread and runs the mappers to convert response into our own models
 * Does try/catch to retrieve the needed information and returns an empty list if fails in both cases
 */
class DashboardService(val retrofit: Retrofit, val conversionRatesMapper: GetConversionRatesMapper, val transactionsMapper: GetTransactionsMapper) {

    suspend fun getConversionRates(): List<ConversionRate> {
        //runs in IO thread, does try/catch to retrieve the needed information and returns an empty list if fails
        return withContext(Dispatchers.IO) {
            try {
                val call = retrofit.create(DashboardRepository::class.java).getConversionRates()
                call.body()?.let { conversionRatesMapper.parseConversionRates(it) } ?: emptyList()
            } catch (e:Exception) {
                throw e
            }
        }
    }

    suspend fun getTransactions(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            try {
                val call = retrofit.create(DashboardRepository::class.java).getTransactions()
                call.body()?.let { transactionsMapper.parseTransactions(it) } ?: emptyList()
            } catch (e: Exception) {
                throw e
            }
        }
    }
}