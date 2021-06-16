package com.dpeco.bemobilegnb.features.dashboard.app.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dpeco.bemobilegnb.databinding.ActivityDashboardBinding
import com.dpeco.bemobilegnb.features.dashboard.app.adapter.TransactionsAdapter
import com.dpeco.bemobilegnb.features.dashboard.app.viewmodel.DashboardViewModel
import com.dpeco.bemobilegnb.utils.AppConstants
import java.io.Serializable

class DashboardActivity : AppCompatActivity(), TransactionsAdapter.Listener {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: TransactionsAdapter

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
                adapter = TransactionsAdapter(it, this@DashboardActivity)
                dashboardRecyclerview.adapter = adapter
            }
        })

        viewModel.showSpinner.observe(this, Observer {
            with (binding) {
                when (it) {
                    true -> {
                        dashboardProgressBar.visibility = View.VISIBLE
                        dashboardRecyclerview.visibility = View.GONE
                        dashboardSearchbar.visibility = View.GONE
                    }
                    false -> {
                        dashboardProgressBar.visibility = View.GONE
                        dashboardRecyclerview.visibility = View.VISIBLE
                        dashboardSearchbar.visibility = View.VISIBLE
                    }
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

            dashboardSearchbar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        filterTransactions(newText)
                    }
                    return false
                }
            })

        }
    }

    private fun filterTransactions(filterString: String) {
        adapter.filterTransactions(filterString)
    }

    private fun callServices() {
        viewModel.callServiceData()
    }

    override fun onMovementClick(position: Int) {
        val intent = Intent(this, TransactionDetailActivity::class.java)
        intent.putExtra(AppConstants.INTENT_EXTRA_TRANSACTION, viewModel.transactions.value?.get(position) as Serializable)
        startActivity(intent)
    }
}