package com.can_apps.common.extensions

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.can_apps.common.R
import com.can_apps.common.layout.SnackbarDecorator
import com.google.android.material.snackbar.Snackbar

private const val MAX_SNACK_BAR_TEXT_LENGTH = 80

fun Fragment.showDefaultError(
    errorMessage: CharSequence? = null,
    optimalViewForSnackBar: View? = view
) {
    withContext { context ->
        val message = errorMessage ?: context.resources.getString(R.string.error_default)

        showError(context, message, optimalViewForSnackBar)
    }
}

private fun showError(context: Context, errorMessage: CharSequence, view: View?) {
    if (view == null || errorMessage.length >= MAX_SNACK_BAR_TEXT_LENGTH) {
        AlertDialog.Builder(context)
            .setMessage(errorMessage)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    } else {
        val snackBar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
        SnackbarDecorator.decorate(context, snackBar)
        snackBar.show()
    }
}

private inline fun Fragment.withContext(block: (Context) -> Unit) {
    context?.let(block)
}
