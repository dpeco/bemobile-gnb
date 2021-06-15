package com.dpeco.bemobilegnb.features.dashboard.repository.mappers

import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import com.dpeco.bemobilegnb.features.dashboard.app.model.TransactionMovement
import com.dpeco.bemobilegnb.features.dashboard.repository.entities.ApiTransaction

class GetTransactionsMapper {

    /**
     * Converts List<ApiTransaction> object from Service to ArrayList<Transaction> for our logic
     * Extra logic since Transaction is slightly modified from ApiTransaction to order data accordingly
     */
    fun parseTransactions(apiTransactions: List<ApiTransaction>): List<Transaction> {
        val transactions: ArrayList<Transaction> = ArrayList()

        // go through every transaction from service call to our model
        for (transaction in apiTransactions) {
            // check if product name is already registered
            var found = false

            val it: ListIterator<Transaction> = transactions.listIterator()
            while (it.hasNext()) {
                val product = it.next()
                if (product.sku == transaction.sku) {
                    val modelTransaction = TransactionMovement(transaction.amount, transaction.currency)
                    product.movements.add(modelTransaction)
                    found = true
                    break
                }
            }

            // add product to the list
            if (!found) {
                val modelTransaction = TransactionMovement(transaction.amount, transaction.currency)
                val modelProduct = Transaction(transaction.sku, ArrayList())
                modelProduct.movements.add(modelTransaction)
                transactions.add(modelProduct)
            }
        }

        return transactions
    }
}