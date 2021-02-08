package test.privat.exchanger.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

fun Int.dpToPx(): Int {
    val density = Resources.getSystem().displayMetrics.density
    return (this * density).roundToInt()
}