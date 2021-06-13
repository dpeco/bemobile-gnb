package com.dpeco.bemobilegnb.features.dashboard.repository

import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiTransaction
import retrofit2.Response
import retrofit2.http.GET

interface DashboardRepository {

    @GET("rates.json")
    suspend fun getConversionRates(): Response<ArrayList<ApiConversionRate>>

    @GET("transactions.json")
    suspend fun getTransactions(): Response<ArrayList<ApiTransaction>>
}