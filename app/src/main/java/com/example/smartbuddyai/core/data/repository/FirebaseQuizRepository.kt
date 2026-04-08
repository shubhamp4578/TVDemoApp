package com.example.smartbuddyai.core.data.repository

import com.example.smartbuddyai.core.data.firebase.FirebaseModule
import com.example.smartbuddyai.data.model.Quiz
import com.example.smartbuddyai.domain.repository.QuizRepository
import kotlinx.coroutines.tasks.await

class FirebaseQuizRepository : QuizRepository {
    private val firestore = FirebaseModule.firebaseStore
    override suspend fun getQuiz(videoId: String): Quiz? {
        return try {
            val doc = firestore.collection("quizzes")
                .document(videoId)
                .get()
                .await()

            doc.toObject(Quiz::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}