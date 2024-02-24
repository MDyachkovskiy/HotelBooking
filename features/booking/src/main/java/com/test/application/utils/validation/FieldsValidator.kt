package com.test.application.utils.validation

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.textfield.TextInputLayout
import com.test.application.R

class FieldsValidator(
    private var context: Context?
) {

    private val errorColor: Int by lazy {
        context?.let { ContextCompat.getColor(it, com.test.application.core.R.color.edit_text_alert) }
            ?: Color.RED
    }

    private val defaultColor: Int by lazy {
        context?.let { ContextCompat.getColor(it, com.test.application.core.R.color.edit_text_background) }
            ?: Color.GREEN
    }

    private val strategies: Map<Int, ValidationStrategy> = mapOf(
        R.id.et_email to EmailValidationStrategy(),
        R.id.et_phone_number to PhoneNumberValidationStrategy(),
        R.id.et_name to BasicValidationStrategy(),
        R.id.et_second_name to BasicValidationStrategy(),
        R.id.et_citizenship to BasicValidationStrategy(),
        R.id.et_birth_date to BasicValidationStrategy(),
        R.id.et_passport_number to BasicValidationStrategy(),
        R.id.et_passport_expiring_date to BasicValidationStrategy()
    )


    fun validateAll(
        initialBlock: View,
        dynamicContainer: ViewGroup,
        emailLayout: TextInputLayout,
        phoneLayout: TextInputLayout
    ): Boolean {
        val allFields = getAllFields(initialBlock, dynamicContainer).toMutableList()
        allFields.add(emailLayout)
        allFields.add(phoneLayout)

        var allValid = true

        allFields.forEach { field ->
            val strategy = strategies[field.id] ?: BasicValidationStrategy()
            val isValid = strategy.validate(field)
            setFieldColorBasedOnValidation(field, isValid)
            if (!isValid) allValid = false
        }

        return allValid
    }

    private fun getAllFields(initialBlock: View, dynamicContainer: ViewGroup): List<TextInputLayout> {
        val initialFields = getTextInputsFromBlock(initialBlock)
        val dynamicFields = mutableListOf<TextInputLayout>()
        for (i in 0 until dynamicContainer.childCount) {
            val child = dynamicContainer.getChildAt(i)
            dynamicFields.addAll(getTextInputsFromBlock(child))
        }
        return initialFields + dynamicFields
    }

    private fun getTextInputsFromBlock(view: View): List<TextInputLayout> {
        return listOf(
            view.findViewById(R.id.et_name),
            view.findViewById(R.id.et_second_name),
            view.findViewById(R.id.et_citizenship),
            view.findViewById(R.id.et_birth_date),
            view.findViewById(R.id.et_passport_number),
            view.findViewById(R.id.et_passport_expiring_date)
        )
    }

    fun validateEmail(emailLayout: TextInputLayout): Boolean {
        val strategy = strategies[R.id.et_email] ?:
        throw IllegalArgumentException(
            context?.getString(com.test.application.core.R.string.email_strategy_error))
        val isValid = strategy.validate(emailLayout)
        setFieldColorBasedOnValidation(emailLayout, isValid)
        return isValid
    }

    fun validatePhoneNumber(phoneLayout: TextInputLayout): Boolean {
        val strategy = strategies[R.id.et_phone_number] ?:
        throw IllegalArgumentException(
            context?.getString(com.test.application.core.R.string.phone_strategy_error))
        val isValid = strategy.validate(phoneLayout)
        setFieldColorBasedOnValidation(phoneLayout, isValid)
        return isValid
    }

    private fun setFieldColorBasedOnValidation(textInputLayout: TextInputLayout, isValid: Boolean) {
        textInputLayout.boxBackgroundColor = if (isValid) defaultColor
        else ColorUtils.setAlphaComponent(errorColor, 38)
    }

    fun cleanup() {
        context = null
    }
}