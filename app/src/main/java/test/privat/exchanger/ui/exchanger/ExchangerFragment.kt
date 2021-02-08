package test.privat.exchanger.ui.exchanger

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import test.privat.exchanger.R
import test.privat.exchanger.base.BaseFragment
import test.privat.exchanger.databinding.ExchangerFragmentBinding
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.ui.exchanger.adapter.ExchangeAdapterNBU
import test.privat.exchanger.ui.exchanger.adapter.ExchangeAdapterPB
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExchangerFragment :
    BaseFragment<ExchangerViewModel, ExchangerFragmentBinding>(R.layout.exchanger_fragment) {

    override val binding: ExchangerFragmentBinding by viewBinding(ExchangerFragmentBinding::bind)
    override val viewModel: ExchangerViewModel by viewModels()

    @Inject
    lateinit var pbAdapter: ExchangeAdapterPB

    @Inject
    lateinit var nbuAdapter: ExchangeAdapterNBU

    override fun setupView() {
        initAdapters()
        with(binding) {
            itemBankPickerNBU.imgCalendar.setOnClickListener { viewModel.fetchExchangeRate("04.02.2020") }
            itemBankPickerPB.imgCalendar.setOnClickListener { viewModel.fetchExchangeRate("04.02.2020") }

            itemBankPickerNBU.txtBank.text =getString(R.string.nbu)
            itemBankPickerPB.txtBank.text =getString(R.string.privat_bank)

            itemBankPickerNBU.txtDate.text ="04.02.2020"
            itemBankPickerPB.txtDate.text ="04.02.2020"
        }
    }

    private fun initAdapters() {
        with(binding) {

            rvPrivat.adapter = pbAdapter
            rvNBU.adapter = nbuAdapter
        }
    }

    override fun bindViewModel() {
        viewModel.exchangeRatesResult.data.observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(exchangeRateConsumer)

        viewModel.exchangeRatesResult.error.observable.subscribe(errorConsumer)
    }

    private val errorConsumer = Consumer<String> {
        Timber.d(it)
    }

    private val exchangeRateConsumer = Consumer<CurrencyData> {
        pbAdapter.fetchData(it.exchangeRate.filter {rate -> rate.purchaseRatePB!=0.0 })
        nbuAdapter.fetchData(it.exchangeRate.filter{rate -> rate.purchaseRateNB!=0.0})
    }
}