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
import test.privat.exchanger.extensions.format
import test.privat.exchanger.ui.datepickerdialog.DatePickerDialogFragment
import test.privat.exchanger.ui.exchanger.adapter.ExchangeAdapterNBU
import test.privat.exchanger.ui.exchanger.adapter.ExchangeAdapterPB
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExchangerFragment :
    BaseFragment<ExchangerViewModel, ExchangerFragmentBinding>(R.layout.exchanger_fragment),
    DatePickerDialogFragment.DatePickerDialogListener {

    override val binding: ExchangerFragmentBinding by viewBinding(ExchangerFragmentBinding::bind)
    override val viewModel: ExchangerViewModel by viewModels()

    @Inject
    lateinit var pbAdapter: ExchangeAdapterPB

    @Inject
    lateinit var nbuAdapter: ExchangeAdapterNBU

    private var selectedPicker = Picker.PB
    override fun setupView() {
        with(binding) {

            rvPrivat.adapter = pbAdapter
            rvNBU.adapter = nbuAdapter

            itemBankPickerNBU.imgCalendar.setOnClickListener {
                selectedPicker = Picker.NBU
                DatePickerDialogFragment().show(
                    childFragmentManager,
                    DatePickerDialogFragment::class.simpleName
                )
            }
            itemBankPickerPB.imgCalendar.setOnClickListener {
                selectedPicker = Picker.PB
                DatePickerDialogFragment().show(
                    childFragmentManager,
                    DatePickerDialogFragment::class.simpleName
                )
            }

            itemBankPickerNBU.txtBank.text = getString(R.string.nbu)
            itemBankPickerPB.txtBank.text = getString(R.string.privat_bank)
        }
    }

    override fun bindViewModel() {
        viewModel.exchangeRatesPBResult.data.observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(exchangePBRateConsumer)

        viewModel.exchangeRatesNBUResult.data.observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(exchangeNBURateConsumer)
    }

    private val exchangeNBURateConsumer = Consumer<CurrencyData> {
        nbuAdapter.fetchData(it.exchangeRate.filter { rate -> rate.purchaseRateNB != 0.0 })
        binding.itemBankPickerNBU.txtDate.text = it.date.format()
    }
    private val exchangePBRateConsumer = Consumer<CurrencyData> {
        pbAdapter.fetchData(it.exchangeRate.filter { rate -> rate.purchaseRatePB != 0.0 })
        binding.itemBankPickerPB.txtDate.text = it.date.format()
    }


    override fun onDateSelected(date: String) {
        viewModel.fetchExchangeRate(date, selectedPicker)
        with(binding) {
            when (selectedPicker) {
                Picker.NBU -> itemBankPickerNBU.txtDate.text = date
                Picker.PB -> itemBankPickerPB.txtDate.text = date
            }
        }
    }

    enum class Picker { PB, NBU }
}