package test.privat.exchanger.extensions

import java.text.SimpleDateFormat
import java.util.*

enum class DateFormat(val tmpl: String) {
    DEFAULT("dd.MM.yyyy")
}

private val LOCALE = Locale("ua")

fun String.toDate(format: DateFormat = DateFormat.DEFAULT): Date? {
    return try {
        val sdf = SimpleDateFormat(format.tmpl, LOCALE)
        Calendar.getInstance()
            .apply { time = sdf.parse(this@toDate) }
            .time
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Date.format(format: DateFormat = DateFormat.DEFAULT): String {
    val sdf = SimpleDateFormat(format.tmpl, LOCALE)
    return sdf.format(this)
}