package com.dpeco.bemobilegnb.utils

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class MoneyConversionUtils {

    companion object {

        /**
         * Formats a money amount into a String, with 2 decimals and RoundingMode.HALF_EVEN
         * @param amount amount to format
         * @return Formatted result
         */
        fun getFormattedAmount(amount: Double): String {
            val bigDecimal = BigDecimal(amount)
            bigDecimal.round(MathContext(2, RoundingMode.HALF_EVEN))
            return "%.2f".format(bigDecimal.toDouble())
        }

        /**
         * Returns the total amount from all the movements of a Transaction object in a single currency
         * @param transaction transaction object to calculate
         * @param conversionRates ArrayList with all available conversion rates
         * @param to result currency we need
         * @return total amount in the desired currency
         */
        fun getTotalConversionAmount(transaction: Transaction, conversionRates: ArrayList<ConversionRate>, to: String): Double {
            var totalAmountInEuros = 0.0;
            for (movement in transaction.movements) {
                totalAmountInEuros += getConversionAmount(movement.amount, conversionRates, movement.currency, to)
            }
            transaction.totalAmountInEuro = totalAmountInEuros
            return totalAmountInEuros
        }

        /**
         * Converts an amount from one currency to another
         * @param amount amount
         * @param conversionRates ArrayList with all available conversion rates
         * @param from result currency we have right now
         * @param to result currency we need
         * @return total amount in the desired currency
         */
        fun getConversionAmount(amount: Double, conversionRates: ArrayList<ConversionRate>, from: String, to: String): Double {
            // In case, if current currency matches with desired return given amount
            if (to == from) {
                return amount
            }
            return amount * getConversionRate(conversionRates, from, to, ArrayList())
        }

        /**
         * Private method for getConversionAmount logic, recursions to find an available route to the currency we want and obtain the conversion rate
         */
        private fun getConversionRate(conversionRates: ArrayList<ConversionRate>, from: String, to: String, checkedRates: ArrayList<String>): Double {
            //first step, get ConversionRates that match our "from"
            val matchingFromConversionRates = getMatchingFromConversionRates(conversionRates, from)
            //second step, attempt to find an option that matches "to"
            val matchingToConversionRates =
                getMatchingToConversionRates(matchingFromConversionRates, to, checkedRates)
            //third step, if "to" match not found, recursively explore other options
            if (!matchingToConversionRates.isNullOrEmpty()) {
                return matchingToConversionRates[0].rate
            } else {
                val it: ListIterator<ConversionRate> = matchingFromConversionRates.listIterator()
                while (it.hasNext()) {
                    val conversionRate = it.next()
                    checkedRates.add(from)
                    val rate =
                        getConversionRate(conversionRates, conversionRate.to, to, checkedRates)
                    return rate * conversionRate.rate
                }
            }

            // Failed to find a conversion rate, thus returns 0
            return 0.0
        }

        /**
         * Private method for getConversionRate logic, filters conversionRates with desired from
         */
        private fun getMatchingFromConversionRates(conversionRates: ArrayList<ConversionRate>, from: String): ArrayList<ConversionRate> {
            val matchingConversionRates: ArrayList<ConversionRate> = ArrayList()

            for (conversionRate in conversionRates) {
                if (conversionRate.from == from) {
                    matchingConversionRates.add(conversionRate)
                }
            }
            return matchingConversionRates
        }

        /**
         * Private method for getConversionRate logic, filters conversionRates with desired to
         */
        private fun getMatchingToConversionRates(conversionRates: ArrayList<ConversionRate>, to: String, checkedRates: ArrayList<String>): ArrayList<ConversionRate> {
            val matchingConversionRates: ArrayList<ConversionRate> = ArrayList()

            for (conversionRate in conversionRates) {
                if (conversionRate.to == to && !checkedRates.contains(to)) {
                    matchingConversionRates.add(conversionRate)
                }
            }
            return matchingConversionRates
        }
    }

    //todo attempt to optimize algorithm, ignored for now
/*
    fun getTotalAmountFromTransaction(transaction: Transaction, conversionRates: ArrayList<ConversionRate>, from: String, to: String): Double {
        var totalAmountInEuros = 0.0;

        var movementsByCurrency: ArrayList<TransactionMovement> = ArrayList()

        for (movement in transaction.movements) {
            val it: ListIterator<TransactionMovement> = movementsByCurrency.listIterator()
            while (it.hasNext()) {
                val movementByCurrency = it.next()
                if (movement.currency == movementByCurrency.currency) {
                    movementByCurrency.amount = movement.amount
                    break
                }
            }
        }

        for (movement in transaction.movements) {
            if (movementsByCurrency.contains(movement)) {
                movementsByCurrency.
            }

            totalAmountInEuros += getConversionAmount(conversionRates, movement.currency, "EUR", movement.amount)
        }
        transaction.totalAmountInEuro = totalAmountInEuros
    }

 */
}