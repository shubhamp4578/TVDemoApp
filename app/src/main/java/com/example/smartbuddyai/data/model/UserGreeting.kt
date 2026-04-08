package com.example.smartbuddyai.data.model

data class UserGreeting(
    val name: String
)

data class ContinueLearning(
    val title: String,
    val subtitle: String,
    val progress: Float,
    val videoId: String
)
