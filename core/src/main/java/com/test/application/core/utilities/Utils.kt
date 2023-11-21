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

fun getOrdinalTourist(touristNumber: Int): String {
    return when (touristNumber) {
        1 -> "Первый турист"
        2 -> "Второй турист"
        3 -> "Третий турист"
        4 -> "Четвертый турист"
        5 -> "Пятый турист"
        6 -> "Шестой турист"
        7 -> "Седьмой турист"
        8 -> "Восьмой турист"
        9 -> "Девятый турист"
        10 -> "Десятый турист"
        else -> "$touristNumber-й турист"
    }
}