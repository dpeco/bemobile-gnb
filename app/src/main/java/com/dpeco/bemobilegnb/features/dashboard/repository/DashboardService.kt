package com.dpeco.bemobilegnb.features.dashboard.repository

import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardService {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://quiet-stone-2094.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun getConversionRates(): List<ApiConversionRate> {
        return withContext(Dispatchers.IO) {
            val call = getRetrofit().create(DashboardRepository::class.java).getConversionRates()
            call.body() ?: emptyList()
        }
    }
    suspend fun getTransactions(): List<ApiTransaction> {
        return withContext(Dispatchers.IO) {
            val call = getRetrofit().create(DashboardRepository::class.java).getTransactions()
            call.body() ?: emptyList()
        }
    }
}