package com.notes.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("MMMM d, yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}