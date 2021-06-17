package com.dpeco.bemobilegnb.features.dashboard.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetConversionRatesUseCase
import com.dpeco.bemobilegnb.features.dashboard.usecases.GetTransactionsUseCase
import com.dpeco.bemobilegnb.GnbApplication
import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.utils.GnbConstants
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by dpeco
 */
class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var conversionRatesUseCase: GetConversionRatesUseCase

    @Inject
    lateinit var transactionsUseCase: GetTransactionsUseCase

    val transactions = MutableLiveData<List<Transaction>>()

    val showMovements = MutableLiveData<Boolean>()
    val showSpinner = MutableLiveData<Boolean>()
    val showEmptyState = MutableLiveData<Boolean>()

    fun initialize() {
        getApplication<GnbApplication>().appComponent.inject(this)
    }

    fun callServiceData() {
        setShowMovements(false)
        setShowSpinner(true)
        //async service calls + data management to get the data we want to display
        viewModelScope.launch {
            val conversionRatesResult: List<ConversionRate>
            val transactionsResult: List<Transaction>

            try {
                conversionRatesResult = conversionRatesUseCase.invoke()
            } catch (e: Exception) {
                setShowSpinner(false)
                setShowEmptyState(true)
                return@launch
            }

            try {
                transactionsResult = transactionsUseCase.invoke()
            } catch (e: Exception) {
                setShowSpinner(false)
                setShowEmptyState(true)
                return@launch
            }

            setShowSpinner(false)
            if (!conversionRatesResult.isNullOrEmpty() && !transactionsResult.isNullOrEmpty()) {
                for (transaction in transactionsResult) {
                    transaction.totalAmount = MoneyConversionUtils.getTotalConversionAmount(transaction, conversionRatesResult, GnbConstants.CURRENCY_EUR)
                }
                transactions.postValue(transactionsResult)
                setShowMovements(true)
            } else {
                // show empty state or error if we don't have information to work with
                setShowEmptyState(true)
            }
        }
    }

    fun setShowMovements(showMovements: Boolean) {
        this.showMovements.value = showMovements
    }
    fun setShowSpinner(showSpinner: Boolean) {
        this.showSpinner.value = showSpinner
    }

    fun setShowEmptyState(showEmptyState: Boolean) {
        this.showEmptyState.value = showEmptyState
    }

}