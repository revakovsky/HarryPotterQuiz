package com.revakovskyi.harrypotterquiz

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.makeToast(textResource: Int) {
    Toast.makeText(this, textResource, Toast.LENGTH_LONG).show()
}