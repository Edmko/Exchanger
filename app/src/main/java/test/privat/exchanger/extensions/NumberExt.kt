package test.privat.exchanger.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Number?.toFormattedPrice(): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = ' '
    symbols.decimalSeparator = ','
    val decimalFormat = DecimalFormat("#,###,###,###.##", symbols)
    return decimalFormat.format(this ?: 0.0)
}