package com.can_apps.common.wrappers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface CommonTimestampWrapper {

    val getOneHourInSeconds: Long
    val currentTimeStampSeconds: Long

    fun toDate(seconds: Long): String
}

class CommonTimestampWrapperDefault : CommonTimestampWrapper {

    override val getOneHourInSeconds: Long
        get() = 3600

    override val currentTimeStampSeconds: Long
        get() = System.currentTimeMillis() / 1000

    override fun toDate(seconds: Long): String { // todo show week if less 7 days Saturday 7:56
        val formatter = SimpleDateFormat("dd/MM hh:mm", Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = seconds*1000
        return formatter.format(calendar.time)
    }
}
