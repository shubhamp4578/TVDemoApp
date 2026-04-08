package com.example.smartbuddyai.data.model

sealed class VideoSource {
    data class Youtube(
        val videoId: String
    ): VideoSource()

    data class Remote(
        val url: String
    ): VideoSource()

    data class Local(
        val filePath: String
    ): VideoSource()
}