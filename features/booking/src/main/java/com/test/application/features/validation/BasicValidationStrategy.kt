package com.test.application.features.validation

import com.google.android.material.textfield.TextInputLayout

class BasicValidationStrategy : ValidationStrategy {
    override fun validate(field: TextInputLayout): Boolean {
        return !field.editText?.text.isNullOrEmpty()
    }
}