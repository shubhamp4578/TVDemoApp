package com.example.smartbuddyai.data.model

data class Course(
    val id: String,
    val title: String,
    val duration: String,
    val videoSource: VideoSource,
    val progress: Float = 0f,
    val thumbnailUrl: String
)
