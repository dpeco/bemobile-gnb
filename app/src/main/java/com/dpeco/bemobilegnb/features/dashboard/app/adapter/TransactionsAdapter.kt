package com.dpeco.bemobilegnb.features.dashboard.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dpeco.bemobilegnb.R
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction

class TransactionsAdapter(private val transactions: List<Transaction>, private val listener: TransactionsAdapter.Listener): RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        return TransactionHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        holder.setData(transactions[position])
    }

    class TransactionHolder(itemView: View, private val listener: Listener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val titleView: TextView = itemView.findViewById(R.id.transaction_item_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun setData(transaction: Transaction) {
            titleView.text = transaction.sku
        }

        override fun onClick(v: View?) {
            listener.onMovementClick(adapterPosition)
        }
    }

    interface Listener {
        fun onMovementClick(position: Int)
    }
}