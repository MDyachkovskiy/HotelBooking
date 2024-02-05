package com.test.application.features

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.test.application.core.utilities.getOrdinalTourist
import com.test.application.databinding.TouristInfoBlockBinding

class TouristInfoManager(
    private val context: Context, private val container: ConstraintLayout
) {
    private var lastAddedView: View? = null
    private var touristCount = 0
    private val resources: Resources = context.resources

    fun addNewTouristBlock() {
        val touristBlockBinding = createTouristInfoBlock()
        updateTouristTitle(touristBlockBinding, ++touristCount)
        setLayoutTopMargin(touristBlockBinding.root)
        addTouristBlockToContainer(touristBlockBinding.root)
        updateLayoutConstraints(touristBlockBinding.root)
        setupOpenCloseButtonListener(touristBlockBinding)
        lastAddedView = touristBlockBinding.root
        setInitialVisibility(touristBlockBinding, true)
    }

    private fun setupOpenCloseButtonListener(touristBlockBinding: TouristInfoBlockBinding) {
        with(touristBlockBinding) {
            openButton.setOnClickListener {
                val isCurrentlyVisible = etName.visibility == View.VISIBLE
                AnimationHelper.animateVisibility(
                    container,
                    listOf(
                        etName,
                        etSecondName,
                        etCitizenship,
                        etBirthDate,
                        etPassportNumber,
                        etPassportExpiringDate
                    ),
                    !isCurrentlyVisible
                )
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
            topMargin = (8 * resources.displayMetrics.density).toInt()
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
            if (lastAddedView == null) ConstraintSet.TOP else ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(container)
    }

    private fun setInitialVisibility(
        touristBlockBinding: TouristInfoBlockBinding, isVisible: Boolean
    ) {
        with(touristBlockBinding) {
            val textInputLayouts = listOf(
                etName,
                etSecondName,
                etCitizenship,
                etBirthDate,
                etPassportNumber,
                etPassportExpiringDate
            )
            textInputLayouts.forEach { layout ->
                layout.visibility = if (isVisible) View.VISIBLE else View.GONE
            }
            openButtonArrow.rotation = if (isVisible) 0f else 180f
        }
    }

}