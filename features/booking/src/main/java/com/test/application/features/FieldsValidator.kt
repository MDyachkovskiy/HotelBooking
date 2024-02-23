package com.test.application.features

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.textfield.TextInputLayout
import com.test.application.R

class FieldsValidator(
    private val context: Context
) {
    private val errorColor = ContextCompat
        .getColor(context, com.test.application.core.R.color.edit_text_alert)
    private val defaultColor = ContextCompat
        .getColor(context, com.test.application.core.R.color.edit_text_background)

    fun validateAll(
        initialBlock: View,
        dynamicContainer: ViewGroup,
        emailLayout: TextInputLayout,
        phoneLayout: TextInputLayout
    ): Boolean {
        val isInitialBlockValid = validateBlock(initialBlock)
        val areDynamicBlocksValid = validateDynamicBlocks(dynamicContainer)
        val isEmailValid = validateEmail(emailLayout)
        val isPhoneValid = validatePhoneNumber(phoneLayout)

        return isInitialBlockValid && areDynamicBlocksValid && isEmailValid && isPhoneValid
    }

    private fun validateBlock(view: View): Boolean {
        val fields = getTextInputsFromBlock(view)
        return validateFields(fields)
    }

    private fun validateDynamicBlocks(container: ViewGroup): Boolean {
        var allValid = true
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            val dynamicFields = getTextInputsFromBlock(child)
            if (!validateFields(dynamicFields)) {
                allValid = false
            }
        }
        return allValid
    }

    private fun validateFields(fields: List<TextInputLayout>): Boolean {
        var allFieldsValid = true
        fields.forEach { field ->
            if (field.editText?.text.isNullOrEmpty()) {
                setFieldColorBasedOnValidation(field,false)
                allFieldsValid = false
            } else {
                setFieldColorBasedOnValidation(field,true)
            }
        }
        return allFieldsValid
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
        val email = emailLayout.editText?.text.toString()
        return if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setFieldColorBasedOnValidation(emailLayout, true)
            true
        } else {
            setFieldColorBasedOnValidation(emailLayout, false)
            false
        }
    }

    fun validatePhoneNumber(phoneLayout: TextInputLayout): Boolean {
        val phone = phoneLayout.editText?.text.toString()
        return if (phone.isNotEmpty()) {
            setFieldColorBasedOnValidation(phoneLayout, true)
            true
        } else {
            setFieldColorBasedOnValidation(phoneLayout, false)
            false
        }
    }

    private fun setFieldColorBasedOnValidation(textInputLayout: TextInputLayout, isValid: Boolean) {
        textInputLayout.boxBackgroundColor = if (isValid) defaultColor
        else ColorUtils.setAlphaComponent(errorColor, 38)
    }

    fun showErrorToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}