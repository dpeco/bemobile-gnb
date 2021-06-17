package com.dpeco.bemobilegnb.features.dashboard.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dpeco.bemobilegnb.R
import com.dpeco.bemobilegnb.databinding.ItemTransactionMovementBinding
import com.dpeco.bemobilegnb.features.dashboard.app.model.TransactionMovement
import com.dpeco.bemobilegnb.utils.MoneyConversionUtils

/**
 * Created by dpeco
 */
class TransactionMovementsAdapter(private val transactions: List<TransactionMovement>): RecyclerView.Adapter<TransactionMovementsAdapter.TransactionMovementHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionMovementHolder {
        val binding = ItemTransactionMovementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionMovementHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TransactionMovementHolder, position: Int) {
        holder.setData(transactions[position], position, itemCount)
    }

    class TransactionMovementHolder(binding: ItemTransactionMovementBinding) : RecyclerView.ViewHolder(binding.root) {
        private val numberView: TextView = itemView.findViewById(R.id.transaction_movement_item_number)
        private val amountView: TextView = itemView.findViewById(R.id.transaction_movement_item_amount)

        fun setData(movement: TransactionMovement, position: Int, size:Int) {
            val adjustedPosition = position + 1
            val numberText = "$adjustedPosition/$size"
            val amountText = MoneyConversionUtils.getFormattedAmountString(movement.amount) + " " + movement.currency
            numberView.text = numberText
            amountView.text = amountText
        }
    }

}