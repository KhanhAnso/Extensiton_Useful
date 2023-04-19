package com.example.useful_extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardManager(private val context: Context, private val view: View) {
    private var imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun hideKeyboard() = imm.hideSoftInputFromWindow(view.windowToken, 0)
    fun showKeyboard() = imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}