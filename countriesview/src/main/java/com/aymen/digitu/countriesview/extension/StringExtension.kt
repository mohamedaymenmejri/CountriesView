package com.aymen.digitu.countriesview.extension

import android.util.Base64
import java.nio.charset.Charset

fun readFileAsString(base64String: String): String {
    val data = Base64.decode(base64String, Base64.DEFAULT)
    return String(data, Charset.forName("UTF-8"))
}