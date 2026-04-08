package com.example.smartbuddyai.data.model

data class VideoProgress(
    val videoId: String = "",
    val position: Long = 0L,
    val duration: Long = 0L,
    val updatedAt: Long = System.currentTimeMillis()
)