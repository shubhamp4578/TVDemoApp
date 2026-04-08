package com.example.smartbuddyai.domain.repository

import com.example.smartbuddyai.data.model.Course
import com.example.smartbuddyai.data.model.VideoProgress

interface VideoRepository {
    suspend fun getVideos(): List<Course>
    suspend fun getVideoById(videoId: String): Course?
    suspend fun saveProgress(progress: VideoProgress)
    suspend fun getProgress(videoId: String) : VideoProgress?
    suspend fun getAllProgress(): List<VideoProgress>
}