package test.privat.exchanger.domain.repository

import io.reactivex.rxjava3.core.Single
import test.privat.exchanger.domain.entities.CurrencyData

interface PrivatMainRepository {
    fun getExchangeRatesByDate(date: String): Single<CurrencyData>
}