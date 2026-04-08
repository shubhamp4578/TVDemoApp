package com.example.smartbuddyai.core.data.repository

import com.example.smartbuddyai.core.data.firebase.FirebaseModule
import com.example.smartbuddyai.data.model.Course
import com.example.smartbuddyai.data.model.VideoProgress
import com.example.smartbuddyai.data.model.VideoSource
import com.example.smartbuddyai.domain.repository.VideoRepository
import kotlinx.coroutines.tasks.await

class FirebaseVideoRepository : VideoRepository {
    private val firestore = FirebaseModule.firebaseStore

    override suspend fun getVideos(): List<Course> {
        return try {
            val snapshot = firestore.collection("videos").get().await()
            snapshot.documents.mapNotNull { doc ->
                Course(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    duration = doc.getString("duration") ?: "",
                    videoSource = VideoSource.Remote(doc.getString("videoUrl") ?: ""),
                    thumbnailUrl = doc.getString("thumbnail") ?: ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getVideoById(videoId: String): Course? {
        return try {
            val doc = firestore.collection("videos").document(videoId).get().await()

            Course(
                id = doc.id,
                title = doc.getString("title") ?: "",
                duration = doc.getString("duration") ?: "",
                videoSource = VideoSource.Remote(doc.getString("videoUrl") ?: ""),
                thumbnailUrl = doc.getString("thumbnail") ?: ""

            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun saveProgress(progress: VideoProgress) {
        try {
            firestore.collection("progress")
                .document("default_${progress.videoId}")
                .set(progress)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getProgress(videoId: String): VideoProgress? {
        return try {
            val doc = firestore.collection("progress")
                .document("default_$videoId")
                .get()
                .await()

            doc.toObject(VideoProgress::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAllProgress(): List<VideoProgress> {
        return try {
            val snapshot = firestore.collection("progress")
                .get()
                .await()

            snapshot.documents.mapNotNull {
                it.toObject(VideoProgress::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}