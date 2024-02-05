package com.test.application.features

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.textfield.TextInputLayout

class FieldsValidator(
    private val context: Context
) {
    private val errorColor = ContextCompat.getColor(context, com.test.application.core.R.color.edit_text_alert)
    private val defaultColor = ContextCompat.getColor(context, com.test.application.core.R.color.edit_text_background)

    fun validateFields(fields: List<TextInputLayout>): Boolean {
        var allFieldsValid = true
        fields.forEach { field ->
            if (field.editText?.text.isNullOrEmpty()) {
                setFieldErrorColor(field)
                allFieldsValid = false
            } else {
                resetEditTextFieldToDefaultColor(field)
            }
        }
        return allFieldsValid
    }

    private fun setFieldErrorColor(textInputLayout: TextInputLayout) {
        val backgroundColor = ColorUtils.setAlphaComponent(errorColor, 38)
        textInputLayout.boxBackgroundColor = backgroundColor
    }

    private fun resetEditTextFieldToDefaultColor(textInputLayout: TextInputLayout) {
        textInputLayout.boxBackgroundColor = defaultColor
    }

    fun showErrorToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}