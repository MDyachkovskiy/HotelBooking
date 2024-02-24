package com.test.application.features.validation

import com.google.android.material.textfield.TextInputLayout

interface ValidationStrategy {
    fun validate(field: TextInputLayout): Boolean
}