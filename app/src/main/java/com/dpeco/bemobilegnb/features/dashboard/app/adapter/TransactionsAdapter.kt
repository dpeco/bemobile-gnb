package com.dpeco.bemobilegnb.features.dashboard.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpeco.bemobilegnb.R
import com.dpeco.bemobilegnb.databinding.ItemTransactionBinding
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils

/**
 * Created by dpeco
 */
class TransactionsAdapter(private val transactions: List<Transaction>, private val listener: Listener): RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    var filteredTransactions = ArrayList(transactions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {

        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return filteredTransactions.size
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        holder.setData(filteredTransactions[position])
    }

    fun filterTransactions(filterString: String) {
        filteredTransactions = ArrayList()

        for(transaction in transactions) {
            if (transaction.sku.contains(filterString, true)) {
                filteredTransactions.add(transaction)
            }
        }
        notifyDataSetChanged()
    }

    class TransactionHolder(private val binding: ItemTransactionBinding, private val listener: Listener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun setData(transaction: Transaction) {
            val amountString = MoneyConversionUtils.getFormattedAmountString(transaction.totalAmount) + itemView.context.getString(R.string.currency_eur)
            with (binding) {
                transactionItemTitle.text = transaction.sku
                transactionItemTotalAmount.text = itemView.context.getString(R.string.dashboard_transaction_item_balance, amountString)
            }
        }

        override fun onClick(v: View?) {
            listener.onMovementClick(adapterPosition)
        }
    }

    interface Listener {
        fun onMovementClick(position: Int)
    }
}