package test.privat.exchanger.data.remote.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import test.privat.exchanger.data.remote.response.GetCurrencyArchiveResponse

interface PrivatApiService {

    @GET("/p24api/exchange_rates?json&")
    fun getExchangeRateByDate(
        @Query ("date") date: String
    ): Single<GetCurrencyArchiveResponse>
}