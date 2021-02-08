package test.privat.exchanger.data.repository

import io.reactivex.rxjava3.core.Single
import test.privat.exchanger.data.remote.api.PrivatApiService
import test.privat.exchanger.data.remote.response.toDomain
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.domain.repository.PrivatMainRepository
import javax.inject.Inject

class PrivatMainRepositoryImpl@Inject constructor(
    private val apiService:PrivatApiService):PrivatMainRepository {

    override fun getExchangeRatesByDate(date: String): Single<CurrencyData> {
        return apiService.getExchangeRateByDate(date).map {
            it.toDomain()
        }
    }
}