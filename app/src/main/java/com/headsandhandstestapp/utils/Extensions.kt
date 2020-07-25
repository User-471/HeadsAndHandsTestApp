package com.headsandhandstestapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar

fun String.isEmail(): Boolean {
    return matches(Regex("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b"))
}

fun String.isAcceptablePassword(): Boolean {
    return matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@\$!%*#?&]{6,}$"))
}

fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
    this.view.setBackgroundColor(colorInt)
    return this
}

fun showKeyboard(context: Context, editText: View) {
    editText.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun hideKeyboard(context: Context, editText: View, parentView: View) {
    editText.clearFocus()
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(parentView.getWindowToken(), 0)
}