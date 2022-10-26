package com.revakovskyi.harrypotterquiz.questions.model

data class Request(
    val endpoints: List<Endpoint>,
    val group: String,
    val max_age: Int
)