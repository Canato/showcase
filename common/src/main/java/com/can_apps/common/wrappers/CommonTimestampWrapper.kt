package com.can_apps.common.wrappers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface CommonTimestampWrapper {

    val currentTimeStampSeconds: Long

    fun toDate(seconds: Long): String
}

class CommonTimestampWrapperDefault : CommonTimestampWrapper {

    companion object {

        const val ONE_DAY_SECONDS = 86400
        const val SEVEN_DAYS_SECONDS = 604800
    }

    override val currentTimeStampSeconds: Long
        get() = System.currentTimeMillis() / 1000

    override fun toDate(seconds: Long): String {

        val isMoreThanOneDays = (currentTimeStampSeconds - seconds) > ONE_DAY_SECONDS
        val isLessThanSevenDays = (currentTimeStampSeconds - seconds) < SEVEN_DAYS_SECONDS

        val pattern = if (isMoreThanOneDays && isLessThanSevenDays) "EEEE hh:mm" else "dd/MM hh:mm"

        val formatter = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = seconds * 1000
        return formatter.format(calendar.time)
    }
}
