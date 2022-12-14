package com.revakovskyi.harrypotterquiz.questions.model

data class QuestionModel(
    val questionId: Int,
    val questionTitle: String,
    val banner: Int,

    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,

    val correctAnswer: Int
)
