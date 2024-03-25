package com.ali.weatherapp.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


var toast: Toast? = null
fun Context.shortToast(msg: String) {
    toast?.cancel()
    toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    toast?.show()
}

fun Fragment.shortToast(msg: String) {
    toast?.cancel()
    toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
    toast?.show()
}