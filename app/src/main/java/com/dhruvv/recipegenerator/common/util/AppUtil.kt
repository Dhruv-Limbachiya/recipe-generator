package com.dhruvv.recipegenerator.common.util

import java.util.Calendar


/**
 * Returns a string indicating the meal by time based on the current system time.
 *
 * This function determines the current hour of the day using the system's current time in milliseconds.
 * It then returns a string representing a meal time (Breakfast, Lunch, Evening Snacks, or Dinner)
 * based on predefined hour ranges.
 *
 * @return A string indicating the meal time corresponding to the current time of day.
 */
fun getMealByTime(): String {
    // Create a Calendar instance and set the current time in milliseconds
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()

    // Get the current hour of the day (0-23)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    // Return the corresponding meal time based on the hour of the day
    return when(hour) {
        // 6:00 AM to 11:59 AM
        in 6..11 -> "Breakfast"
        // 12:00 PM to 5:59 PM
        in 12..17 -> "Lunch"
        // 6:00 PM to 8:59 PM
        in 18..20 -> "Evening Snacks"
        // 9:00 PM to 5:59 AM
        else -> "Dinner"
    }
}