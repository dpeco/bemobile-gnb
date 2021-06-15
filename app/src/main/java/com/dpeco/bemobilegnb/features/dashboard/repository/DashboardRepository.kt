package com.dpeco.bemobilegnb.features.dashboard.repository

import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiConversionRate
import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiTransaction
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface DashboardRepository {

    @GET("rates.json")
    @Headers("Accept: application/json")
    suspend fun getConversionRates(): Response<List<ApiConversionRate>>

    @GET("transactions.json")
    @Headers("Accept: application/json")
    suspend fun getTransactions(): Response<List<ApiTransaction>>
}