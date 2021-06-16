package com.dpeco.bemobilegnb.features.dashboard.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dpeco.bemobilegnb.databinding.ActivityTransactionDetailBinding
import com.dpeco.bemobilegnb.features.dashboard.app.adapter.TransactionMovementsAdapter
import com.dpeco.bemobilegnb.features.dashboard.app.viewmodel.TransactionDetailViewModel

/**
 * Created by dpeco
 * Extra activity to show in more detail every transaction received
 */
class TransactionDetailActivity: AppCompatActivity() {

    private lateinit var viewModel: TransactionDetailViewModel
    private lateinit var binding: ActivityTransactionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TransactionDetailViewModel::class.java]
        viewModel.getExtras(intent)

        setToolbar()
        setObservers()
    }

    private fun setToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setObservers() {
        viewModel.detailTitleText.observe(this, Observer {
            with (binding) {
                transactionDetailTitle.text = it
            }
        })

        viewModel.balanceText.observe(this, Observer {
            with (binding) {
                val balanceString = getString(com.dpeco.bemobilegnb.R.string.currency_eur) + it
                transactionBalanceAmount.text = balanceString
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