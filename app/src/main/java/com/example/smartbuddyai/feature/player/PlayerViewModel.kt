package com.example.smartbuddyai.feature.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartbuddyai.core.data.repository.FirebaseVideoRepository
import com.example.smartbuddyai.data.model.Course
import com.example.smartbuddyai.data.model.VideoProgress
import com.example.smartbuddyai.domain.repository.VideoRepository
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val repository: VideoRepository
) : ViewModel() {
    var video by mutableStateOf<Course?>(null)
        private set

    fun loadVideo(videoId: String) {
        viewModelScope.launch {
            video = repository.getVideoById(videoId)
        }
    }

    fun saveProgress(progress: VideoProgress) {
        viewModelScope.launch {
            repository.saveProgress(progress)
        }
    }

    suspend fun getProgress(videoId: String): VideoProgress? {
        return repository.getProgress(videoId)
    }
}


class PlayerViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = FirebaseVideoRepository()

        return PlayerViewModel(repository) as T
    }
}