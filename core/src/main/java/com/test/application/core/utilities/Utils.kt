package com.test.application.core.utilities

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Int?): String {
    return if(price != null) {
        val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
        "от ${formatter.format(price)} ₽"
    } else {
       "Цену уточняйте"
    }
}

fun formatExactPrice(price: Int?): String {
    return if(price != null) {
        val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
        "${formatter.format(price)} ₽"
    } else {
        "Цену уточняйте"
    }
}