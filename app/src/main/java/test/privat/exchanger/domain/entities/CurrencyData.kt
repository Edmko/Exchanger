package test.privat.exchanger.domain.entities

import java.util.*

data class CurrencyData(
    val date: Date,
    val bank: String,
    val baseCurrencyCode: Int,
    val baseCurrency: String,
    val exchangeRate: List<ExchangeRate>
){
    data class ExchangeRate(
        val baseCurrency: String,
        val currency: String,
        val saleRateNB: Double,
        val purchaseRateNB: Double,
        val saleRatePB: Double,
        val purchaseRatePB: Double
    )
}