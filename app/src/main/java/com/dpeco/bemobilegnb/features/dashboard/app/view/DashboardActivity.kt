package com.dpeco.bemobilegnb.features.dashboard.app.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dpeco.bemobilegnb.databinding.ActivityDashboardBinding
import com.dpeco.bemobilegnb.features.dashboard.app.adapter.TransactionsAdapter
import com.dpeco.bemobilegnb.features.dashboard.app.viewmodel.DashboardViewModel

class DashboardActivity : AppCompatActivity(), TransactionsAdapter.Listener {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        setObservers()
        setListeners()
        callServices()
    }

    private fun setObservers() {
        viewModel.transactions.observe(this, Observer {
            with (binding) {
                val adapter = TransactionsAdapter(it, this@DashboardActivity)
                dashboardRecyclerview.adapter = adapter
            }
        })

        viewModel.showSpinner.observe(this, Observer {
            with (binding) {
                when (it) {
                    true -> {dashboardProgressBar.visibility = View.VISIBLE}
                    false -> {dashboardProgressBar.visibility = View.GONE}
                }
            }
        })

        viewModel.showEmptyState.observe(this, Observer {
            with (binding) {
                when (it) {
                    true -> {dashboardEmptyState.visibility = View.VISIBLE}
                    false -> {dashboardEmptyState.visibility = View.GONE}
                }
            }
        })
    }

    private fun setListeners() {
        with (binding) {
            dashboardEmptyStateButton.setOnClickListener(View.OnClickListener {
                viewModel.hideEmptyState()
                callServices()
            })
        }
    }

    private fun callServices() {
        viewModel.callServiceData()
    }

    override fun onMovementClick(position: Int) {
        viewModel.launchMovementDetailActivity(this, position)
    }
}