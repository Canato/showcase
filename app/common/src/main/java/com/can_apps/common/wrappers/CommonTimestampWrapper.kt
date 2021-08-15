package com.can_apps.common.wrappers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface CommonTimestampWrapper {

    val currentTimeStampMillis: Long

    fun toDate(millis: Long): String
}

class CommonTimestampWrapperDefault : CommonTimestampWrapper {

    companion object {

        const val ONE_DAY_SECONDS = 86400
        const val SEVEN_DAYS_SECONDS = 604800
    }

    override val currentTimeStampMillis: Long
        get() = System.currentTimeMillis()

    override fun toDate(millis: Long): String {

        val isMoreThanOneDays = (currentTimeStampMillis - millis) / 1000 > ONE_DAY_SECONDS
        val isLessThanSevenDays = (currentTimeStampMillis - millis) / 1000 < SEVEN_DAYS_SECONDS

        val pattern = if (isMoreThanOneDays && isLessThanSevenDays) "EEEE hh:mm" else "dd/MM hh:mm"

        val formatter = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return formatter.format(calendar.time)
    }
}
