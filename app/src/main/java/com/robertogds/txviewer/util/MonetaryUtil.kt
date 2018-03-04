package com.robertogds.txviewer.util

import com.robertogds.txviewer.util.annotations.Mockable
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * A class for various currency related operations, mostly converting between types and formats.
 */
@Mockable
class MonetaryUtil(unit: Int) {

    private lateinit var btcFormat: DecimalFormat
    private lateinit var ethFormat: DecimalFormat
    private lateinit var fiatFormat: DecimalFormat
    private var unit: Int = 0

    init {
        updateUnit(unit)
    }

    /**
     * Updates the currently stored BTC format type as well as the [Locale].
     *
     * @param unit The chosen BTC unit
     */
    final fun updateUnit(unit: Int) {
        this.unit = unit

        val defaultLocale = Locale.getDefault()

        fiatFormat = (NumberFormat.getInstance(defaultLocale) as DecimalFormat).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }

        btcFormat = (NumberFormat.getInstance(defaultLocale) as DecimalFormat).apply {
            minimumFractionDigits = 1
            maximumFractionDigits = when (unit) {
                MonetaryUtil.MICRO_BTC -> 2
                MonetaryUtil.MILLI_BTC -> 5
                MonetaryUtil.UNIT_BTC -> 8
                else -> throw IllegalArgumentException("Invalid BTC format $unit")
            }
        }

        ethFormat = (NumberFormat.getInstance(defaultLocale) as DecimalFormat).apply {
            maximumFractionDigits = 18
            minimumFractionDigits = 2
        }
    }

    /**
     * Returns the current BTC format as a [NumberFormat] object.
     *
     * @return A [NumberFormat] object with the correct decimal fractions for the current BTC format
     */
    fun getBtcFormat() = btcFormat

    /**
     * Returns the Fiat format as a [NumberFormat] object for a given currency code.
     *
     * @param fiat The currency code (ie USD) for the format you wish to return
     * @return A [NumberFormat] object with the correct decimal fractions for the chosen Fiat format
     * @see ExchangeRateFactory.getCurrencyLabels
     */
    fun getFiatFormat(fiat: String) = fiatFormat.apply { currency = Currency.getInstance(fiat) }

    /**
     * Returns the current BTC format as a [NumberFormat] object.
     *
     * @return A [NumberFormat] object with the correct decimal fractions for the current BTC format
     */
    fun getEthFormat() = ethFormat

    /**
     * Returns an [Array] clone of the [BTC_UNITS] array.
     *
     * @return An [Array] containing the list of BTC formats as [String]
     * @see [BTC_UNITS]
     */
    fun getBtcUnits(): Array<String> = BTC_UNITS.clone()

    /**
     * Returns an [Array] clone of the [BCH_UNITS] array.
     *
     * @return An [Array] containing the list of BCH formats as [String]
     * @see [BCH_UNITS]
     */
    fun getBchUnits(): Array<String> = BCH_UNITS.clone()

    /**
     * Returns a specific BTC Unit [String] for a given BTC unit type
     *
     * @param unit The chosen BTC unit
     * @return A BTC format string
     * @see [BTC_UNITS]
     */
    fun getBtcUnit(unit: Int) = BTC_UNITS[unit]

    /**
     * Returns a specific BCH Unit [String] for a given BCH unit type
     *
     * @param unit The chosen BCH unit
     * @return A BCH format string
     * @see [BCH_UNITS]
     */
    fun getBchUnit(unit: Int) = BCH_UNITS[unit]

    /**
     * Returns a specific ETHER Unit [String] for a given ETH unit type
     *
     * @param unit The chosen ETH unit
     * @return A ETH format string
     * @see [ETH_UNITS]
     */
    fun getEthUnit(unit: Int) = ETH_UNITS[unit]

    /**
     * Accepts a [Long] value in Satoshis and returns the display amount as a [String] based on the
     * chosen [unit] type. Compared to [getDisplayAmountWithFormatting], this method does not return
     * Strings formatted to a particular region, and therefore don't feature delimiters (ie returns
     * "1000.0", not "1,000.0).
     *
     * eg. 10_000 Satoshi -> "0.0001" when unit == UNIT_BTC
     *
     * @param value The amount to be formatted in Satoshis
     * @return An amount formatted as a [String]
     */
    fun getDisplayAmount(value: Long): String = when (unit) {
        MonetaryUtil.MICRO_BTC -> ((value * MICRO_LONG).toDouble() / BTC_DEC).toString()
        MonetaryUtil.MILLI_BTC -> ((value * MILLI_LONG).toDouble() / BTC_DEC).toString()
        else -> getBtcFormat().format(value / BTC_DEC)
    }

    /**
     * Accepts a [Long] value which is the number of [unit]s and returns their value in BTC as a
     * [BigInteger] object.
     *
     * eg. 1_000_000 mBTC -> 1000 BTC
     *
     * @param value The amount to be denominated, which can be in any BTC unit format
     * @return The amount as a [BigInteger] representing the value in BTC
     */
    fun getUndenominatedAmount(value: Long): BigInteger = when (unit) {
        MonetaryUtil.MICRO_BTC -> BigInteger.valueOf(value / MICRO_LONG)
        MonetaryUtil.MILLI_BTC -> BigInteger.valueOf(value / MILLI_LONG)
        else -> BigInteger.valueOf(value)
    }

    /**
     * Accepts a [Double] value which is the number of [unit]s and returns their value in BTC as a
     * [Double].
     *
     * eg. 1_000_000 mBTC -> 1000 BTC
     *
     * @param value The amount to be undenominated, which can be in any BTC unit format
     * @return The amount as a [Double] representing the value in BTC
     */
    fun getUndenominatedAmount(value: Double): Double = when (unit) {
        MonetaryUtil.MICRO_BTC -> value / MICRO_DOUBLE
        MonetaryUtil.MILLI_BTC -> value / MILLI_DOUBLE
        else -> value
    }

    /**
     * Accepts a [Double] value which represents the amount of [UNIT_BTC] and returns a [Double]
     * which is the equivalent in the chosen BTC format.
     *
     * eg 1.0 BTC -> 1_000.0 mBTC
     *
     * @param value The amount to be denominated in BTC
     * @return The amount as a [Double] representing the value in the chosen format
     */
    fun getDenominatedAmount(value: Double): Double = when (unit) {
        MonetaryUtil.MICRO_BTC -> value * MICRO_DOUBLE
        MonetaryUtil.MILLI_BTC -> value * MILLI_DOUBLE
        else -> value
    }

    /**
     * Accepts a [Long] value in Satoshis and returns the display amount as a [String] based on the
     * chosen [unit] type. This method adds delimiters based on the [Locale].
     *
     * eg. 10_000_000_000 Satoshi -> "100,000.0" when unit == MILLI_BTC
     *
     * @param value The amount to be formatted in Satoshis
     * @return An amount formatted as a [String]
     */
    fun getDisplayAmountWithFormatting(value: Long): String {
        val df = DecimalFormat().apply {
            minimumIntegerDigits = 1
            minimumFractionDigits = 1
            maximumFractionDigits = 8
        }

        return when (unit) {
            MonetaryUtil.MICRO_BTC -> df.format((value * MICRO_LONG).toDouble() / BTC_DEC)
            MonetaryUtil.MILLI_BTC -> df.format((value * MILLI_LONG).toDouble() / BTC_DEC)
            else -> getBtcFormat().format(value / BTC_DEC) + " BTC"
        }
    }

    /**
     * Accepts a [Double] value in Satoshis and returns the display amount as a [String] based on the
     * chosen [unit] type. This method adds delimiters based on the [Locale].
     *
     * eg. 10_000_000_000.0 Satoshi -> "100,000.0" when unit == MILLI_BTC
     *
     * @param value The amount to be formatted in Satoshis
     * @return An amount formatted as a [String]
     */
    fun getDisplayAmountWithFormatting(value: Double): String {
        val df = DecimalFormat().apply {
            minimumIntegerDigits = 1
            minimumFractionDigits = 1
            maximumFractionDigits = 8
        }

        return when (unit) {
            MonetaryUtil.MICRO_BTC -> df.format(value * MICRO_DOUBLE / BTC_DEC)
            MonetaryUtil.MILLI_BTC -> df.format(value * MILLI_DOUBLE / BTC_DEC)
            else -> getBtcFormat().format(value / BTC_DEC)
        }
    }

    /**
     * Accepts a [Double] value in fiat currency and returns a [String] formatted to the region
     * with the correct currency symbol. For example, 1.2345 with country code "USD" and locale
     * [Locale.UK] would return "US$1.23".
     *
     * @param amount The amount of fiat currency to be formatted as a [Double]
     * @param currencyCode The 3-letter currency code, eg. "GBP"
     * @param locale The current device [Locale]
     * @return The formatted currency [String]
     */
    fun getFiatDisplayString(amount: Double, currencyCode: String, locale: Locale): String {
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
        numberFormat.decimalFormatSymbols = decimalFormatSymbols.apply {
            this.currencySymbol = getCurrencySymbol(currencyCode, locale)
        }
        return numberFormat.format(amount)
    }

    /**
     * Returns the symbol for the chosen currency, based on the passed currency code and the chosen
     * device [Locale].
     *
     * @param currencyCode The 3-letter currency code, eg. "GBP"
     * @param locale The current device [Locale]
     * @return The correct currency symbol (eg. "$")
     */
    fun getCurrencySymbol(currencyCode: String, locale: Locale): String =
            Currency.getInstance(currencyCode).getSymbol(locale)

    companion object {
        /**
         * BTC Unit type constants
         */
        const val UNIT_BTC = 0
        const val MILLI_BTC = 1
        const val MICRO_BTC = 2

        private const val MILLI_DOUBLE = 1000.0
        private const val MICRO_DOUBLE = 1000000.0
        private const val MILLI_LONG = 1000L
        private const val MICRO_LONG = 1000000L
        private const val BTC_DEC = 1e8
        private val BTC_UNITS = arrayOf("BTC", "mBTC", "bits")

        /**
         * BCH Unit type constants
         */
        private val BCH_UNITS = arrayOf("BCH", "mBCH", "bits")

        /**
         * ETH Unit type constants
         */
        const val UNIT_ETH = 0
        private val ETH_UNITS = arrayOf("ETH")
    }

}
