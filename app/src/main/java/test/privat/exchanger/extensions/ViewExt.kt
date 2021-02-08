package test.privat.exchanger.extensions

import android.widget.DatePicker

fun DatePicker.getDateString(): String {
    val year = year
    val day = if (dayOfMonth<10)"0$dayOfMonth" else dayOfMonth
    val tempMonth = month+1
    val month = if (tempMonth<10)"0$tempMonth" else tempMonth
    return "$day.${month}.$year"
}