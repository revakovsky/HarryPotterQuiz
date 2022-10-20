package com.revakovskyi.harrypotterquiz.questions

import android.content.Context
import com.revakovskyi.harrypotterquiz.R
import com.revakovskyi.harrypotterquiz.models.QuestionModel

class QuestionsList(context: Context) {

    private val question1 = QuestionModel(
        1,
        context.getString(R.string.question1),
        R.drawable.image1,
        context.getString(R.string.option1_1),
        context.getString(R.string.option1_2),
        context.getString(R.string.option1_3),
        context.getString(R.string.option1_4),
        context.resources.getInteger(R.integer.correct_answer1)
    )

    private val question2 = QuestionModel(
        2,
        context.getString(R.string.question2),
        R.drawable.image2,
        context.getString(R.string.option2_1),
        context.getString(R.string.option2_2),
        context.getString(R.string.option2_3),
        context.getString(R.string.option2_4),
        context.resources.getInteger(R.integer.correct_answer2)
    )

    private val question3 = QuestionModel(
        3,
        context.getString(R.string.question3),
        R.drawable.image3,
        context.getString(R.string.option3_1),
        context.getString(R.string.option3_2),
        context.getString(R.string.option3_3),
        context.getString(R.string.option3_4),
        context.resources.getInteger(R.integer.correct_answer3)
    )

    private val question4 = QuestionModel(
        4,
        context.getString(R.string.question4),
        R.drawable.image4,
        context.getString(R.string.option4_1),
        context.getString(R.string.option4_2),
        context.getString(R.string.option4_3),
        context.getString(R.string.option4_4),
        context.resources.getInteger(R.integer.correct_answer4)
    )

    private val question5 = QuestionModel(
        5,
        context.getString(R.string.question5),
        R.drawable.image5,
        context.getString(R.string.option5_1),
        context.getString(R.string.option5_2),
        context.getString(R.string.option5_3),
        context.getString(R.string.option5_4),
        context.resources.getInteger(R.integer.correct_answer5)
    )

    private val question6 = QuestionModel(
        6,
        context.getString(R.string.question6),
        R.drawable.image6,
        context.getString(R.string.option6_1),
        context.getString(R.string.option6_2),
        context.getString(R.string.option6_3),
        context.getString(R.string.option6_4),
        context.resources.getInteger(R.integer.correct_answer6)
    )

    private val question7 = QuestionModel(
        7,
        context.getString(R.string.question7),
        R.drawable.image7,
        context.getString(R.string.option7_1),
        context.getString(R.string.option7_2),
        context.getString(R.string.option7_3),
        context.getString(R.string.option7_4),
        context.resources.getInteger(R.integer.correct_answer7)
    )

    fun getQuestions(): ArrayList<QuestionModel> {
        val questionsList = arrayListOf<QuestionModel>()
        questionsList.add(question1)
        questionsList.add(question2)
        questionsList.add(question3)
        questionsList.add(question4)
        questionsList.add(question5)
        questionsList.add(question6)
        questionsList.add(question7)
        return questionsList
    }

}