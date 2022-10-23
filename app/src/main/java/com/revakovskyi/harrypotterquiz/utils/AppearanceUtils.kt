package com.revakovskyi.harrypotterquiz.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.revakovskyi.harrypotterquiz.R

class AppearanceUtils(private val context: Context) {

    private val choosingOption1 = 1
    private val choosingOption2 = 2
    private val choosingOption3 = 3
    private val choosingOption4 = 4

    fun getQuestionBackground(questionId: Int): Drawable? {
        return when (questionId) {
            1 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question1, null)
            2 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question2, null)
            3 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question3, null)
            4 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question4, null)
            5 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question5, null)
            6 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question6, null)
            7 -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question7, null)

            else -> ResourcesCompat.getDrawable(context.resources, R.drawable.bg_question1, null)
        }
    }

    fun setDefaultOptionLook(optionsList: List<TextView>) {
        for (option in optionsList) {
            option.setTextColor(getOptionColor(R.color.gray))
            option.background = getOptionBackground(R.drawable.option_bg)
        }
    }

    fun setStyle(fieldOption: TextView, drawableResource: Int) {
        fieldOption.setTextColor(getOptionColor(R.color.base))
        fieldOption.background = getOptionBackground(drawableResource)
    }

    private fun getOptionBackground(drawableResource: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            context.resources, drawableResource, null
        )
    }

    private fun getOptionColor(colorResource: Int): Int {
        return ResourcesCompat.getColor(context.resources, colorResource, null)
    }

    fun setAnsweredOptionLook(answer: Int, drawableResource: Int, optionsList: List<TextView>) {
        when (answer) {
            choosingOption1 -> setStyle(optionsList[choosingOption1 - 1], drawableResource)
            choosingOption2 -> setStyle(optionsList[choosingOption2 - 1], drawableResource)
            choosingOption3 -> setStyle(optionsList[choosingOption3 - 1], drawableResource)
            choosingOption4 -> setStyle(optionsList[choosingOption4 - 1], drawableResource)
        }
    }

    fun isOptionActivated(optionsList: List<TextView>): Boolean {
        return optionsList[choosingOption1 - 1].isActivated ||
                optionsList[choosingOption2 - 1].isActivated ||
                optionsList[choosingOption3 - 1].isActivated ||
                optionsList[choosingOption4 - 1].isActivated
    }

}