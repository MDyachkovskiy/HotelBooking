package com.test.application.features.validation

import com.google.android.material.textfield.TextInputLayout

class EmailValidationStrategy : ValidationStrategy {
    override fun validate(field: TextInputLayout): Boolean {
        val email = field.editText?.text.toString()
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}