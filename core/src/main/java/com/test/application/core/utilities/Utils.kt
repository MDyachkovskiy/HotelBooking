package com.test.application.core.utilities

import android.content.Context
import android.content.res.Resources
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.test.application.core.R
import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Int?, resources: Resources): String {
    return if(price != null) {
        val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
        "от ${formatter.format(price)} ₽"
    } else {
        resources.getString(R.string.price_inquiry)
    }
}

fun formatExactPrice(price: Int?, resources: Resources): String {
    return if(price != null) {
        val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
        "${formatter.format(price)} ₽"
    } else {
        resources.getString(R.string.price_inquiry)
    }
}

fun getOrdinalTourist(touristNumber: Int, resources: Resources): String {
    return when (touristNumber) {
        1 -> resources.getString(R.string.first_tourist)
        2 -> resources.getString(R.string.second_tourist)
        3 -> resources.getString(R.string.third_tourist)
        4 -> resources.getString(R.string.fourth_tourist)
        5 -> resources.getString(R.string.fifth_tourist)
        6 -> resources.getString(R.string.sixth_tourist)
        7 -> resources.getString(R.string.seventh_tourist)
        8 -> resources.getString(R.string.eighth_tourist)
        9 -> resources.getString(R.string.ninth_tourist)
        10 -> resources.getString(R.string.tenth_tourist)
        else -> String.format(resources.getString(R.string.nth_tourist), touristNumber)
    }
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