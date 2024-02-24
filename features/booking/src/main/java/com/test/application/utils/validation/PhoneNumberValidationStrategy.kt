package com.test.application.utils.validation

import com.google.android.material.textfield.TextInputLayout

class PhoneNumberValidationStrategy : ValidationStrategy {
    override fun validate(field: TextInputLayout): Boolean {
        val phone = field.editText?.text.toString()
        return phone.isNotEmpty() && phone.length >=18
    }
}