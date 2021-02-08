package test.privat.exchanger.ui.exchanger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.privat.exchanger.databinding.ItemExchangeBinding
import test.privat.exchanger.domain.entities.CurrencyData
import javax.inject.Inject

class ExchangeAdapterPB @Inject constructor(): RecyclerView.Adapter<ExchangeAdapterPB.ViewHolder>() {

    var findExchanger : ((String) -> Unit)? = null
    private val exchangeRates = arrayListOf<CurrencyData.ExchangeRate>()
    fun fetchData(data: List<CurrencyData.ExchangeRate>) {
        exchangeRates.clear()
        exchangeRates.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemExchangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(binding) {
                root.setOnClickListener { findExchanger?.invoke(exchangeRates[position].currency) }
                txtCurrency.text = exchangeRates[position].currency
                txtSell.text = String.format("%.3f",exchangeRates[position].saleRatePB)
                txtBuy.text = String.format("%.3f",exchangeRates[position].purchaseRatePB)
            }
        }
    }

    override fun getItemCount() = exchangeRates.size

    inner class ViewHolder(val binding: ItemExchangeBinding) : RecyclerView.ViewHolder(binding.root)
}