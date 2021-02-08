package test.privat.exchanger.ui.exchanger

import dagger.hilt.android.lifecycle.HiltViewModel
import test.privat.exchanger.base.BaseViewModel
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.domain.interactor.GetCurrencyExchangeRatesByDateUseCase
import test.privat.exchanger.extensions.format
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExchangerViewModel @Inject constructor(private val getCurrencyExchangeRatesByDateUseCase: GetCurrencyExchangeRatesByDateUseCase) :
    BaseViewModel() {

    val exchangeRatesPBResult = UseCaseResult<CurrencyData>()
    val exchangeRatesNBUResult = UseCaseResult<CurrencyData>()

    init {
        fetchPBExchangeRate(Date().format())
        fetchNBUExchangeRate(Date().format())
    }

    fun fetchExchangeRate(params: String, selectedPicker: ExchangerFragment.Picker) {
        when (selectedPicker) {
            ExchangerFragment.Picker.NBU -> fetchNBUExchangeRate(params)
            ExchangerFragment.Picker.PB -> fetchPBExchangeRate(params)
        }
    }

    fun fetchPBExchangeRate(params: String) {
        getCurrencyExchangeRatesByDateUseCase.params(params)
            .subscribe(explodeTo(exchangeRatesPBResult)).autoDispose()
    }

    fun fetchNBUExchangeRate(params: String) {
        getCurrencyExchangeRatesByDateUseCase.params(params)
            .subscribe(explodeTo(exchangeRatesNBUResult)).autoDispose()
    }
}