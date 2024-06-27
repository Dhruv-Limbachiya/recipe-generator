package com.dhruvv.recipegenerator.common.util

import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

/**
 * Extension function to convert a Long timestamp to a formatted date string.
 * If the Android SDK version is Oreo (API level 26) or higher, it uses the LocalDate API.
 * Otherwise, it uses SimpleDateFormat for compatibility with older versions.
 *
 * @return Formatted date string in "yyyy-MM-dd" format.
 */
fun Long.toDate(): String {
    // Check if the SDK version is Oreo (API level 26) or higher
    val formattedDate =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Use LocalDate to get the current date and convert it to a string
            LocalDate.now().toString()
        } else {
            // Use SimpleDateFormat to format the date for older versions
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.format(this)
        }

    // If formattedDate is null, fall back to getting the date using Calendar
    return formattedDate ?: getCalendarDate()
}

/**
 * Function to get the current date using Calendar.
 *
 * @return Current date string in "yyyy-MM-dd" format.
 */
fun getCalendarDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-indexed, so add 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Format the date as "yyyy-MM-dd" using String.format
    return String.format("%04d-%02d-%02d", year, month, day)
}


fun formatDate(dateString: String,format: String = "dd MMM"): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat(format, Locale.getDefault())
        val dateTime = inputFormat.parse(dateString)
        val outputDate = dateTime?.let { outputFormat.format(it) }
        return outputDate ?: ""
    }catch (e:Exception) {
        Log.e("Extension", "formatDate: ", e)
    }
    return ""
}