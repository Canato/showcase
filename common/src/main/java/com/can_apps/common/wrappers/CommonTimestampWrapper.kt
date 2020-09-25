package com.can_apps.common.wrappers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface CommonTimestampWrapper {

    val getOneHourInSeconds: Long
    val getOneDayInSeconds: Long
    val getSevenDaysInSeconds: Long
    val currentTimeStampSeconds: Long

    fun toDate(seconds: Long): String
}

class CommonTimestampWrapperDefault : CommonTimestampWrapper {

    override val getOneHourInSeconds: Long
        get() = 3600

    override val getOneDayInSeconds: Long
        get() = 86400

    override val getSevenDaysInSeconds: Long
        get() = 604800

    override val currentTimeStampSeconds: Long
        get() = System.currentTimeMillis() / 1000

    override fun toDate(seconds: Long): String {

        val isMoreThanOneDays = (currentTimeStampSeconds - seconds) > getOneDayInSeconds
        val isLessThanSevenDays = (currentTimeStampSeconds - seconds) < getSevenDaysInSeconds

        val pattern = if (isMoreThanOneDays && isLessThanSevenDays) "EEEE hh:mm" else "dd/MM hh:mm"

        val formatter = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = seconds * 1000
        return formatter.format(calendar.time)
    }
}
