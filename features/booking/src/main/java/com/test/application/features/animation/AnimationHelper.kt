package com.test.application.features.animation

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager

object AnimationHelper {
    fun animateVisibility(
        viewGroup: ViewGroup,
        isVisible: Boolean,
        duration: Long = 300
    ) {
        TransitionManager.beginDelayedTransition(viewGroup, AutoTransition().apply {
            this.duration = duration
        })
        viewGroup.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    fun animateArrowRotation(arrowView: ImageView, isExpanded: Boolean, duration: Long = 300) {
        val rotationAngle = if (isExpanded) 0f else 180f
        ObjectAnimator.ofFloat(arrowView, "rotation", rotationAngle).apply {
            this.duration = duration
            start()
        }
    }
}