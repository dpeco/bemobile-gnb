package com.dpeco.bemobilegnb.features.dashboard.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dpeco.bemobilegnb.R
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils

class TransactionsAdapter(private val transactions: List<Transaction>, private val listener: Listener): RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    var filteredTransactions = ArrayList(transactions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        return TransactionHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false), listener)
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

    class TransactionHolder(itemView: View, private val listener: Listener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val titleView: TextView = itemView.findViewById(R.id.transaction_item_title)
        private val amountView: TextView = itemView.findViewById(R.id.transaction_item_total_amount)

        init {
            itemView.setOnClickListener(this)
        }

        fun setData(transaction: Transaction) {
            val amountString = itemView.context.getString(R.string.currency_eur) + MoneyConversionUtils.getFormattedAmount(transaction.totalAmountInEuro)
            titleView.text = transaction.sku
            amountView.text = itemView.context.getString(R.string.dashboard_transaction_item_balance, amountString)
        }

        override fun onClick(v: View?) {
            listener.onMovementClick(adapterPosition)
        }
    }

    interface Listener {
        fun onMovementClick(position: Int)
    }
}