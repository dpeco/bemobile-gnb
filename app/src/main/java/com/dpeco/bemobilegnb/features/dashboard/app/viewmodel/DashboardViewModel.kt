package com.dpeco.bemobilegnb.features.dashboard.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetConversionRatesUseCase
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetTransactionsUseCase
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    val transactions = MutableLiveData<List<Transaction>>()
    val showSpinner = MutableLiveData<Boolean>()
    val showEmptyState = MutableLiveData<Boolean>()

    fun callServiceData() {
        showSpinner()
        //async service calls + data management to get the data we want to display
        viewModelScope.launch {
            val conversionRatesResult = GetConversionRatesUseCase().invoke()
            val transactionsResult = GetTransactionsUseCase().invoke()

            hideSpinner()
            if (!conversionRatesResult.isNullOrEmpty() && !transactionsResult.isNullOrEmpty()) {
                for (transaction in transactionsResult) {
                    transaction.totalAmountInEuro = MoneyConversionUtils.getTotalConversionAmount(transaction, conversionRatesResult, "EUR")
                }
                transactions.postValue(transactionsResult)
            } else {
                // show empty state or error if we failed to get the information to work with
                showEmptyState()
            }
        }
    }

    fun showSpinner() {
        showSpinner.value = true
    }

    fun hideSpinner() {
        showSpinner.value = false
    }

    fun showEmptyState() {
        showEmptyState.value = true
    }

    fun hideEmptyState() {
        showEmptyState.value = false
    }
}