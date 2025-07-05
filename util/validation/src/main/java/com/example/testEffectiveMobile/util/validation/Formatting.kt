package com.example.testEffectiveMobile.util.validation

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(inputDate: String): String {
    val date = LocalDate.parse(inputDate, DateTimeFormatter.ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    return date.format(formatter).replaceFirstChar { it.uppercase() }
}
@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(dateString: String): LocalDate? {
    return try {
        LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
    } catch (e: Exception) {
        null
    }
}