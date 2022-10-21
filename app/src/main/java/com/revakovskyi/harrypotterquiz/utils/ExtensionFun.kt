package com.revakovskyi.harrypotterquiz.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.makeToast(textResource: Int) {
    Toast.makeText(this, textResource, Toast.LENGTH_LONG).show()
}

fun Fragment.makeToast(textResource: Int) {
    Toast.makeText(requireActivity(), textResource, Toast.LENGTH_LONG).show()
}

fun View.hideSoftInput() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}