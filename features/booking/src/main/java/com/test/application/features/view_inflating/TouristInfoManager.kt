package com.test.application.features.view_inflating

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.test.application.core.utilities.getOrdinalTourist
import com.test.application.databinding.TouristInfoBlockBinding
import com.test.application.features.animation.AnimationHelper

class TouristInfoManager(
    private var context: Context?,
    private val container: ConstraintLayout
) {
    private var lastAddedView: View? = null
    private var touristCount = 1
    private val resources: Resources = context!!.resources

    fun addNewTouristBlock() {
        val touristBlockBinding = createTouristInfoBlock()
        updateTouristTitle(touristBlockBinding, ++touristCount)
        setLayoutTopMargin(touristBlockBinding.root)
        addTouristBlockToContainer(touristBlockBinding.root)
        updateLayoutConstraints(touristBlockBinding.root)
        setupOpenCloseButtonListener(touristBlockBinding)
        lastAddedView = touristBlockBinding.root
        setInitialVisibility(touristBlockBinding)
    }

    private fun setupOpenCloseButtonListener(touristBlockBinding: TouristInfoBlockBinding) {
        with(touristBlockBinding) {
            openButton.setOnClickListener {
                val isCurrentlyVisible = textBlock.visibility == View.VISIBLE

                AnimationHelper.animateVisibility(textBlock, !isCurrentlyVisible)

                touristBlockBinding.textBlock.visibility =
                    if (isCurrentlyVisible) View.GONE else View.VISIBLE

                AnimationHelper.animateArrowRotation(openButtonArrow, !isCurrentlyVisible)
            }
        }
    }

    private fun createTouristInfoBlock(): TouristInfoBlockBinding {
        val touristInfoBlockBinding = TouristInfoBlockBinding.inflate(
            LayoutInflater.from(context), container, false
        )
        if (touristInfoBlockBinding.root.id == View.NO_ID) {
            touristInfoBlockBinding.root.id = View.generateViewId()
        }
        return touristInfoBlockBinding
    }

    private fun addTouristBlockToContainer(touristInfoBlock: View) {
        container.addView(touristInfoBlock)
    }

    private fun updateTouristTitle(
        touristBlockBinding: TouristInfoBlockBinding, touristCount: Int
    ) {
        touristBlockBinding.touristInformationBlockTitle.text =
            getOrdinalTourist(touristCount, resources)
    }

    private fun setLayoutTopMargin(touristInfoBlock: View) {
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin =
                resources.getDimension(com.test.application.core.R.dimen.margin_8dp_small).toInt()
        }
        touristInfoBlock.layoutParams = layoutParams
    }

    private fun updateLayoutConstraints(touristInfoBlock: View) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)
        constraintSet.connect(
            touristInfoBlock.id,
            ConstraintSet.TOP,
            lastAddedView?.id ?: ConstraintSet.PARENT_ID,
            if (lastAddedView == null) ConstraintSet.TOP else ConstraintSet.BOTTOM,
            resources.getDimension(com.test.application.core.R.dimen.margin_8dp_small).toInt()
        )

        constraintSet.connect(
            touristInfoBlock.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            touristInfoBlock.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )

        constraintSet.applyTo(container)
    }

    private fun setInitialVisibility(
        touristBlockBinding: TouristInfoBlockBinding, isVisible: Boolean = true
    ) {
        with(touristBlockBinding) {
            root.visibility = if (isVisible) View.VISIBLE else View.GONE
            openButtonArrow.rotation = if (isVisible) 0f else 180f
        }
    }

    fun cleanup() {
        context = null
        container.removeAllViews()
    }
}