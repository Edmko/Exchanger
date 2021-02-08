package test.privat.exchanger.ui.exchanger

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.domain.interactor.GetCurrencyExchangeRatesByDateUseCase
import test.privat.exchanger.domain.repository.PrivatMainRepository
import javax.inject.Inject

@HiltViewModel
class ExchangerViewModel @Inject constructor(private val getCurrencyExchangeRatesByDateUseCase: GetCurrencyExchangeRatesByDateUseCase): ViewModel() {

}