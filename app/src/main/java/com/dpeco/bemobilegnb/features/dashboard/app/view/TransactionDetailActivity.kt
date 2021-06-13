package com.dpeco.bemobilegnb.features.dashboard.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dpeco.bemobilegnb.databinding.ActivityTransactionDetailBinding
import com.dpeco.bemobilegnb.features.dashboard.app.adapter.TransactionMovementsAdapter
import com.dpeco.bemobilegnb.features.dashboard.app.viewmodel.TransactionDetailViewModel

class TransactionDetailActivity: AppCompatActivity() {

    private lateinit var viewModel: TransactionDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TransactionDetailViewModel::class.java]
        viewModel.getExtras(intent)

        viewModel.detailTitleText.observe(this, Observer {
            with (binding) {
                transactionDetailTitle.text = it
            }
        })

        viewModel.balanceText.observe(this, Observer {
            with (binding) {
                transactionBalanceAmount.text = it
            }
        })

        viewModel.transactionMovements.observe(this, Observer {
            with (binding) {
                val adapter = TransactionMovementsAdapter(it)
                transactionDetailRecyclerview.adapter = adapter
            }
        })
    }
}