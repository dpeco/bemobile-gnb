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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        viewModel.getServiceData()

        viewModel.transactions.observe(this, Observer {
            with (binding) {
                val adapter = TransactionsAdapter(it, this@DashboardActivity)
                dashboardRecyclerview.adapter = adapter
            }
        })

        viewModel.showProgress.observe(this, Observer {
            with (binding) {
                when (it) {
                    true -> {dashboardProgressBar.visibility = View.VISIBLE}
                    false -> {dashboardProgressBar.visibility = View.GONE}
                }
            }
        })
    }

    override fun onMovementClick(position: Int) {
        viewModel.launchMovementDetailActivity(this, position)
    }
}