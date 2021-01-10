package com.can_apps.common.wrappers

import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

interface CommonCalendarWrapper {

    fun getDayOfWeek(): Int
}

class CommonCalendarWrapperDefault @Inject constructor() : CommonCalendarWrapper {

    /**
     *  1 -> "Sunday"
     *  2 -> "Monday"
     *  3 -> "Tuesday"
     *  4 -> "Wednesday"
     *  5 -> "Thursday"
     *  6 -> "Friday"
     *  7 -> "Saturday"
     */
    override fun getDayOfWeek(): Int =
        Calendar.getInstance(Locale.GERMAN).get(Calendar.DAY_OF_WEEK)
}
