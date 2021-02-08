package test.privat.exchanger.ui.datepickerdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import test.privat.exchanger.databinding.DialogDatePickerBinding
import test.privat.exchanger.extensions.getDateString
import test.privat.exchanger.extensions.requireListener

class DatePickerDialogFragment : DialogFragment() {

    lateinit var binding: DialogDatePickerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DialogDatePickerBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    private val parentFeature: DatePickerDialogListener?
        get() = requireListener()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnAccept.setOnClickListener {
                parentFeature?.onDateSelected(datePicker.getDateString())
                dismiss()
            }
            datePicker.maxDate = System.currentTimeMillis()
            datePicker.minDate = System.currentTimeMillis() - FOUR_YEARS_IN_MILLIS
        }
    }


    interface DatePickerDialogListener {
        fun onDateSelected(date: String)
    }

    companion object {
        const val FOUR_YEARS_IN_MILLIS = 126227808000L
    }
}