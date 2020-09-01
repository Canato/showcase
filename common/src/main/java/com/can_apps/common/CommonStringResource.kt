package com.can_apps.common

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

class CommonStringResource(private val context: Context) {

    fun getString(@StringRes stringResId: Int): String = context.getString(stringResId)

    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any?): String =
        context.getString(stringResId, *formatArgs)

    fun getStringArray(stringArrayResId: Int): List<String> =
        listOf(*context.resources.getStringArray(stringArrayResId))

    fun getQuantityString(@PluralsRes quantityStringResId: Int, size: Int): String? =
        context.resources?.getQuantityString(quantityStringResId, size)

    fun getQuantityStringWithFormatting(
        @PluralsRes quantityStringResId: Int,
        count: Int,
        vararg formatArgs: Any?
    ): String? = context.resources?.getQuantityString(quantityStringResId, count, *formatArgs)
}
