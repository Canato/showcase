package com.can_apps.common.layout

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.can_apps.common.R
import com.google.android.material.snackbar.Snackbar

object SnackbarDecorator {
    fun decorate(context: Context?, snackbar: Snackbar) {
        snackbar.setActionTextColor(ContextCompat.getColor(context!!, R.color.white))
        val snackBarView = snackbar.view

        try {
            val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)
            textView?.setTextColor(ContextCompat.getColor(context, R.color.red))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        snackBarView.setBackgroundColor(Color.BLACK)
    }
}
