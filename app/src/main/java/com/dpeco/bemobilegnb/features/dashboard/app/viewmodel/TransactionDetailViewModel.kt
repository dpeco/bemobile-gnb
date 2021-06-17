package com.dpeco.bemobilegnb.features.dashboard.app.viewmodel

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.app.model.TransactionMovement
import com.dpeco.bemobilegnb.utils.GnbConstants
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils

/**
 * Created by dpeco
 */
class TransactionDetailViewModel: ViewModel() {

    val detailTitleText = MutableLiveData<String>()
    val balanceText = MutableLiveData<String>()
    val transactionMovements = MutableLiveData<List<TransactionMovement>>()

    fun getExtras(intent: Intent) {

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(GnbConstants.INTENT_EXTRA_TRANSACTION) == true) {
            val transaction = bundle.getSerializable(GnbConstants.INTENT_EXTRA_TRANSACTION) as Transaction
            detailTitleText.value = transaction.sku
            balanceText.value = MoneyConversionUtils.getFormattedAmountString(transaction.totalAmount)
            transactionMovements.value = transaction.movements
        }
    }
}