package test.privat.exchanger.ui.exchanger

import dagger.hilt.android.lifecycle.HiltViewModel
import test.privat.exchanger.base.BaseViewModel
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.domain.interactor.GetCurrencyExchangeRatesByDateUseCase
import javax.inject.Inject

@HiltViewModel
class ExchangerViewModel @Inject constructor(private val getCurrencyExchangeRatesByDateUseCase: GetCurrencyExchangeRatesByDateUseCase) :
    BaseViewModel() {

    val exchangeRatesResult = UseCaseResult<CurrencyData>()

    fun fetchExchangeRate(params: String) {
        getCurrencyExchangeRatesByDateUseCase.params(params)
            .subscribe(explodeTo(exchangeRatesResult)).autoDispose()
    }
}