package com.can_apps.common.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

fun View.showFadeIn() {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
            .setListener(null)
    }
}

fun View.hideFadeOut(finalVisibility: Int = View.INVISIBLE) {
    this.animate()
        .alpha(0f)
        .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
        .setListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    visibility = finalVisibility
                }
            }
        )
}
