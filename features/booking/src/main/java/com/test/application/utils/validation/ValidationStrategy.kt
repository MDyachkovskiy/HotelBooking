package com.test.application.utils.validation

import com.google.android.material.textfield.TextInputLayout

interface ValidationStrategy {
    fun validate(field: TextInputLayout): Boolean
}