package test.privat.exchanger.ui.exchanger

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import test.privat.exchanger.R
import test.privat.exchanger.base.BaseFragment
import test.privat.exchanger.databinding.ExchangerFragmentBinding

class ExchangerFragment : BaseFragment<ExchangerViewModel, ExchangerFragmentBinding>(R.layout.exchanger_fragment) {

    override val binding: ExchangerFragmentBinding by viewBinding(ExchangerFragmentBinding::bind)
    override val viewModel: ExchangerViewModel by viewModels()


    override fun setupView() {
    }

    override fun bindViewModel() {
    }
}