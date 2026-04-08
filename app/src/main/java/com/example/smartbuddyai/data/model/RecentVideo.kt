package com.example.smartbuddyai.data.model

data class RecentVideo(
    val id: String,
    val title: String,
    val duration: String,
    val progress: Float,
    val source: VideoSource,
    val thumbnailUrl:String
)
