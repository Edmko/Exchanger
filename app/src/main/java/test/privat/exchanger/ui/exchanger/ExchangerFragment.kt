package test.privat.exchanger.ui.exchanger

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.functions.Consumer
import test.privat.exchanger.R
import test.privat.exchanger.base.BaseFragment
import test.privat.exchanger.databinding.ExchangerFragmentBinding
import test.privat.exchanger.domain.entities.CurrencyData
import test.privat.exchanger.extensions.dpToPx
import test.privat.exchanger.extensions.format
import test.privat.exchanger.extensions.getDateString
import test.privat.exchanger.ui.exchanger.adapter.ExchangeAdapterNBU
import test.privat.exchanger.ui.exchanger.adapter.ExchangeAdapterPB
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

    private var selectedPicker = Picker.PB

    override fun setupView() {
        with(binding) {

            rvPrivat.adapter = pbAdapter
            rvNBU.adapter = nbuAdapter
            pbAdapter.findExchanger = { currency ->
                selectCurrency(currency)
            }

            itemBankPickerNBU.imgCalendar.setOnClickListener {
                selectedPicker = Picker.NBU
                showPickerDialog()
            }
            itemBankPickerPB.imgCalendar.setOnClickListener {
                selectedPicker = Picker.PB
                showPickerDialog()
            }

            itemBankPickerNBU.txtBank.text = getString(R.string.nbu)
            itemBankPickerPB.txtBank.text = getString(R.string.privat_bank)
        }
    }

    override fun bindViewModel() {
        viewModel.exchangeRatesPBResult.data bind exchangePBRateConsumer

        viewModel.exchangeRatesNBUResult.data bind exchangeNBURateConsumer
    }

    private fun showPickerDialog() {
        val dialog = DatePickerDialog(requireContext())
        dialog.apply {
            datePicker.maxDate = System.currentTimeMillis()
            datePicker.minDate =
                System.currentTimeMillis() - FOUR_YEARS_IN_MILLIS
            setOnDateSetListener { datePicker, i, i2, i3 ->
                onDateSelected(datePicker.getDateString())
            }
        }
        dialog.show()
    }

    private val exchangeNBURateConsumer = Consumer<CurrencyData> {
        nbuAdapter.fetchData(it.exchangeRate.filter { rate -> rate.purchaseRateNB != 0.0 })
        binding.itemBankPickerNBU.txtDate.text = it.date.format()
    }

    private val exchangePBRateConsumer = Consumer<CurrencyData> {
        pbAdapter.fetchData(it.exchangeRate.filter { rate -> rate.purchaseRatePB != 0.0 })
        binding.itemBankPickerPB.txtDate.text = it.date.format()
    }


    private fun onDateSelected(date: String) {
        viewModel.fetchExchangeRate(date, selectedPicker)
        with(binding) {
            when (selectedPicker) {
                Picker.NBU -> itemBankPickerNBU.txtDate.text = date
                Picker.PB -> itemBankPickerPB.txtDate.text = date
            }
        }
    }

    private fun selectCurrency(currency: String) {
        val pos = nbuAdapter.getCurrencyPos(currency) ?: 0
        binding.rvNBU.smoothScrollToPosition(pos)
        val dy = binding.rvNBU.y.toInt() + (pos * 50).dpToPx()
        binding.nestedContainer.smoothScrollTo(0, dy)

    }

    companion object {
        const val FOUR_YEARS_IN_MILLIS = 126227808000L
    }

    enum class Picker { PB, NBU }
}