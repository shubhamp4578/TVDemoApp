package com.example.smartbuddyai.domain.repository

import com.example.smartbuddyai.data.model.Quiz

interface QuizRepository {
    suspend fun getQuiz(videoId: String): Quiz?
}