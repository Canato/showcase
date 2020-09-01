package com.can_apps.common

import java.util.Calendar

interface CommonCalendarWrapper {

    fun getDayOfWeek(): Int
}

class CommonCalendarWrapperDefault : CommonCalendarWrapper {

    /**
     *  1 -> "Monday"
     *  2 -> "Tuesday"
     *  3 -> "Wednesday"
     *  4 -> "Thursday"
     *  5 -> "Friday"
     *  6 -> "Saturday"
     *  7 -> "Sunday"
     */
    override fun getDayOfWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        return calendar.get(Calendar.DAY_OF_WEEK)
    }
}