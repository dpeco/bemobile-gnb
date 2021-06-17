package com.dpeco.bemobilegnb.utils

import com.dpeco.bemobilegnb.features.dashboard.app.model.ConversionRate
import com.dpeco.bemobilegnb.features.dashboard.app.model.Transaction
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by dpeco
 * Stores "static" utilities related with money (formatting and currency conversion)
 */
class MoneyConversionUtils {

    companion object {

        /**
         * Formats a money amount into a String, with 2 decimals and RoundingMode.HALF_EVEN
         * @param amount amount to format
         * @return Formatted result
         */
        fun getFormattedAmountString(amount: Double): String {
            val decimalFormat = DecimalFormat("#,###,##0.00")
            decimalFormat.roundingMode = RoundingMode.HALF_EVEN
            return decimalFormat.format(amount)
        }
        /**
         *
         * Converts amount into a Double, with 2 decimals and RoundingMode.HALF_EVEN
         * @param amount amount to format
         * @return Formatted result
         */
        fun getFormattedAmountDouble(amount: Double): Double {
            var decimal = BigDecimal(amount)
            decimal = decimal.setScale(2, RoundingMode.HALF_EVEN)
            return decimal.toDouble()
        }

        /**
         * Returns the total amount from all the movements of a Transaction object in a single currency
         * @param transaction transaction object to calculate
         * @param conversionRates List with all available conversion rates
         * @param to result currency we need
         * @return total amount in the desired currency
         */
        fun getTotalConversionAmount(transaction: Transaction, conversionRates: List<ConversionRate>, to: String): Double {
            var totalAmountInEuros = 0.0;
            for (movement in transaction.movements) {
                val amountInEuro = getConversionAmount(movement.amount, conversionRates, movement.currency, to)
                totalAmountInEuros += amountInEuro
            }
            transaction.totalAmount = totalAmountInEuros
            return getFormattedAmountDouble(totalAmountInEuros)
        }

        /**
         * Converts an amount from one currency to another
         * @param amount amount
         * @param conversionRates List with all available conversion rates
         * @param from result currency we have right now
         * @param to result currency we need
         * @return total amount in the desired currency
         */
        fun getConversionAmount(amount: Double, conversionRates: List<ConversionRate>, from: String, to: String): Double {
            // In case, if current currency matches with desired return given amount
            if (to == from) {
                return amount
            }

            val conversionRate = getConversionRate(conversionRates, from, to, ArrayList())
            return getFormattedAmountDouble(amount * conversionRate)
        }

        /**
         * Private method for getConversionAmount logic, recursions to find an available route to the currency we want and obtain the conversion rate
         */
        private fun getConversionRate(conversionRates: List<ConversionRate>, from: String, to: String, checkedRates: ArrayList<String>): Double {
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
        private fun getMatchingFromConversionRates(conversionRates: List<ConversionRate>, from: String): List<ConversionRate> {
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
        private fun getMatchingToConversionRates(conversionRates: List<ConversionRate>, to: String, checkedRates: ArrayList<String>): List<ConversionRate> {
            val matchingConversionRates: ArrayList<ConversionRate> = ArrayList()

            for (conversionRate in conversionRates) {
                if (conversionRate.to == to && !checkedRates.contains(to)) {
                    matchingConversionRates.add(conversionRate)
                }
            }
            return matchingConversionRates
        }
    }
}