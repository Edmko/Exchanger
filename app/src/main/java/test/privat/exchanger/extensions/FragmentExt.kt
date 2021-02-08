package test.privat.exchanger.extensions

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.requireListener(): T = requireListener(T::class.java)

fun <T> Fragment.requireListener(listenerClass: Class<T>): T {
    return findListener(listenerClass)
        ?: throw IllegalStateException("Not parentFragment, neither activity implements listener ${listenerClass.simpleName}")
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.findListener(listenerClass: Class<T>): T? {
    var fragment = this
    while (fragment.parentFragment != null) {
        fragment = fragment.parentFragment!!
        if ((listenerClass.isInstance(fragment))) {
            return fragment as T
        }
    }
    return if (listenerClass.isInstance(activity)) activity as T? else null
}