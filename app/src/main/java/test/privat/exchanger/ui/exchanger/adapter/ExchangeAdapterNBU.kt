package test.privat.exchanger.ui.exchanger.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
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
    private var selectedItemPos: Int? = null

    fun getCurrencyPos(currency: String): Int? {
        val item = exchangeRates.firstOrNull { it.currency == currency }
        val pos = selectedItemPos
        selectedItemPos = null
        notifyItemChanged(pos ?: 0)
        if (item != null) {
            selectedItemPos = exchangeRates.indexOf(item)
            notifyItemChanged(selectedItemPos ?: 0)
        }
        return selectedItemPos
    }

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
                root.bindHolderBackgroundColor(position)

            }
        }
    }

    private fun View.bindHolderBackgroundColor(position: Int) {

        val color = when {
            position == selectedItemPos -> R.color.blue_light
            (position % 2) == 0 -> R.color.white
            (position % 2) == 1 -> R.color.honeydew
            else -> R.color.white
        }
        setBackgroundColor(resources.getColor(color, null))
    }

    @SuppressLint("SetTextI18n")
    private fun TextView.bindCost(position: Int) {
        val value = if (exchangeRates[position].saleRateNB < 1.0) 100 else 1
        text = String.format(
            "%.2f",
            exchangeRates[position].saleRateNB * value
        ) + " ${exchangeRates[position].baseCurrency}"
    }

    @SuppressLint("SetTextI18n")
    private fun TextView.bindValue(position: Int) {
        val value = if (exchangeRates[position].saleRateNB < 1.0) 100 else 1
        text = "$value ${exchangeRates[position].currency}"
    }

    override fun getItemCount() = exchangeRates.size

    inner class ViewHolder(val binding: ItemExchangeNbuBinding) :
        RecyclerView.ViewHolder(binding.root)
}