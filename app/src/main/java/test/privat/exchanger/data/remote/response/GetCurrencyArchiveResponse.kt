package test.privat.exchanger.data.remote.response

import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.extensions.toDate
import java.util.*

data class GetCurrencyArchiveResponse(
    val date: String?,
    val bank: String?,
    val baseCurrency: Int?,
    val baseCurrencyLit: String?,
    val exchangeRate: List<ExchangeRate?>
) {
    data class ExchangeRate(
        val baseCurrency: String?,
        val currency: String?,
        val saleRateNB: Double?,
        val purchaseRateNB: Double?,
        val saleRate: Double?,
        val purchaseRate: Double?
    )
}

fun GetCurrencyArchiveResponse.toDomain(): CurrencyData {
    return CurrencyData(
        date = date?.toDate() ?: Date(),
        bank = bank ?: "",
        baseCurrencyCode = baseCurrency ?: 0,
        baseCurrency = baseCurrencyLit ?: "",
        exchangeRate = exchangeRate.map {
            CurrencyData.ExchangeRate(
                it?.baseCurrency ?: "",
                it?.currency ?: "",
                it?.saleRateNB ?: 0.0,
                it?.purchaseRateNB ?: 0.0,
                it?.saleRate ?: 0.0,
                it?.purchaseRate ?: 0.0
            )
        }.filter { rate -> rate.currency.isNotBlank() })
}