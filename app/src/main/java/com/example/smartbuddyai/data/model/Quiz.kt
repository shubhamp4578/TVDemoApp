package com.example.smartbuddyai.data.model

data class Quiz(
    val videoId: String = "",
    val questions: List<Question> = emptyList()
)

data class Question(
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctIndex: Int = 0
)