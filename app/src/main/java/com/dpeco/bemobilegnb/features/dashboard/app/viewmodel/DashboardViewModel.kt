package com.dpeco.bemobilegnb.features.dashboard.app.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpeco.bemobilegnb.features.dashboard.app.view.TransactionDetailActivity
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetConversionRatesUseCase
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetTransactionsUseCase
import com.dpeco.bemobilegnb.utils.AppConstants
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils
import kotlinx.coroutines.launch
import java.io.Serializable

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
                    println("PRINTTEST.5.5.5 - Euro amount:  " + transaction.totalAmountInEuro)
                }
                transactions.postValue(transactionsResult)
            } else {
                // show empty state or error if we failed to get the information to work with
                showEmptyState()
            }
        }
    }

    fun launchMovementDetailActivity(context: Context, position: Int) {
        val intent = Intent(context, TransactionDetailActivity::class.java)
        intent.putExtra(AppConstants.INTENT_EXTRA_TRANSACTION, transactions.value?.get(position) as Serializable)
        context.startActivity(intent)
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