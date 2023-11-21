package com.test.application.core.utilities

import android.content.Context
import android.text.TextUtils
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

fun isValidEmail(target: CharSequence): Boolean {
    return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun setupDoneActionForEditText(editText: EditText){
    editText.setOnEditorActionListener { textView, actionId, keyEvent ->
        if(actionId == EditorInfo.IME_ACTION_DONE ||
            keyEvent?.action == KeyEvent.ACTION_DOWN &&
            keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = textView.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)
            textView.clearFocus()
            true
        } else {
            false
        }
    }
}