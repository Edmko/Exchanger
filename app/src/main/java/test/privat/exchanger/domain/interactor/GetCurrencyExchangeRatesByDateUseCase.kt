package test.privat.exchanger.domain.interactor

import io.reactivex.rxjava3.core.Single
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.domain.repository.PrivatMainRepository

class GetCurrencyExchangeRatesByDateUseCase(private val privateRepository: PrivatMainRepository): UseCase<String, CurrencyData>() {
    override fun createSingle(params: String?): Single<CurrencyData> {
        return privateRepository.getExchangeRatesByDate(params!!)
    }
}