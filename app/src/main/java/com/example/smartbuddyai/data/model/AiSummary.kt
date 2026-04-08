package com.example.smartbuddyai.data.model

data class AiSummary(
    val points: List<String>,
    val notes: List<AiNotes>
)

data class AiNotes(
    val timeStamp: String,
    val text: String
)