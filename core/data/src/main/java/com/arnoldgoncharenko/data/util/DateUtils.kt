package com.arnoldgoncharenko.data.util

import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class DateUtils @Inject constructor() {
    fun calculateDaysLeft(dueDate: String?, targetDate: String): Int {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        if (dueDate != null) {
            val parsedDueDate = dateFormatter.parse(dueDate)
            val parsedTargetDate = dateFormatter.parse(targetDate)

            if (parsedDueDate != null && parsedTargetDate != null) {
                return ChronoUnit.DAYS.between(parsedTargetDate.toInstant(), parsedDueDate.toInstant()).toInt()
            }
        }

        return 0
    }

    fun getCurrentDate(): String {
        return getDateFormatter().format(Calendar.getInstance().getTime())
    }

    fun modifyDate(currentDate: String, increment: Int): String {
        val resultingDate = getDateFormatter().parse(currentDate) ?: return ""

        val calendar = Calendar.getInstance()
        calendar.time = resultingDate
        calendar.add(Calendar.MILLISECOND, increment)

        return getDateFormatter().format(calendar.time)
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"

        fun getDateFormatter(): SimpleDateFormat {
            return SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        }
    }
}