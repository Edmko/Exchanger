package test.privat.exchanger.ui.exchanger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.privat.exchanger.R
import test.privat.exchanger.databinding.ItemExchangeNbuBinding
import test.privat.exchanger.domain.entities.CurrencyData
import javax.inject.Inject

class ExchangeAdapterNBU @Inject constructor() :
    RecyclerView.Adapter<ExchangeAdapterNBU.ViewHolder>() {

    private val exchangeRates = arrayListOf<CurrencyData.ExchangeRate>()
    fun fetchData(data: List<CurrencyData.ExchangeRate>) {
        exchangeRates.clear()
        exchangeRates.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemExchangeNbuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(binding) {

                txtCurrency.text = exchangeRates[position].currency
                txtCurrencyValue.bindValue(position)
                txtExchangeCost.bindCost(position)
                when (position % 2) {
                    0 -> binding.root.setBackgroundColor(
                        binding.root.resources.getColor(
                            R.color.white,
                            null
                        )
                    )
                    1 -> binding.root.setBackgroundColor(
                        binding.root.resources.getColor(
                            R.color.honeydew,
                            null
                        )
                    )
                }
            }
        }
    }

    fun TextView.bindCost(position: Int) {
        val value = if (exchangeRates[position].saleRateNB < 1.0) 100 else 1
        text = String.format(
            "%.2f",
            exchangeRates[position].saleRateNB * value
        ) + " ${exchangeRates[position].baseCurrency}"
    }

    fun TextView.bindValue(position: Int) {
        val value = if (exchangeRates[position].saleRateNB < 1.0) 100 else 1
        text = "$value ${exchangeRates[position].currency}"
    }

    override fun getItemCount() = exchangeRates.size

    inner class ViewHolder(val binding: ItemExchangeNbuBinding) :
        RecyclerView.ViewHolder(binding.root)
}