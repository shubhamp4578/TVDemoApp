package com.example.smartbuddyai.feature.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartbuddyai.core.data.repository.FirebaseVideoRepository
import com.example.smartbuddyai.data.model.ContinueLearning
import com.example.smartbuddyai.data.model.HomeSection
import com.example.smartbuddyai.data.model.RecentVideo
import com.example.smartbuddyai.data.model.UserGreeting
import com.example.smartbuddyai.domain.repository.VideoRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    context: Context,
    private val repository: VideoRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set
    var greeting by mutableStateOf(
        UserGreeting(name = "Shubham")
    )
        private set
    var sections: List<HomeSection> by mutableStateOf(emptyList())
        private set

    init {
        loadSections()
    }

    private fun loadSections() {
        viewModelScope.launch {
            isLoading = true
            val videos = repository.getVideos()
            val progressList = repository.getAllProgress()
            val progressMap = progressList.associateBy { it.videoId }
            val updatedVideos = videos.map { video ->
                val progress = progressMap[video.id]

                val percent = if (progress != null && progress.duration > 0) {
                    progress.position.toFloat() / progress.duration
                } else 0f

                video.copy(progress = percent)
            }
            val latestProgress = progressList.maxByOrNull { it.updatedAt }
            val continueLearning = latestProgress?.let { progress ->
                val video = updatedVideos.find { it.id == progress.videoId }
                video?.let {
                    ContinueLearning(
                        title = it.title,
                        subtitle = "Resume Learning",
                        progress = if (progress.duration > 0)
                            (progress.position.toFloat() / progress.duration)
                        else 0f,
                        videoId = it.id
                    )
                }
            }
            val continueWatching = progressList.mapNotNull { progressList ->
                val video = updatedVideos.find { it.id == progressList.videoId }
                video?.let {
                    RecentVideo(
                        id = it.id,
                        title = it.title,
                        duration = it.duration,
                        progress = if (progressList.duration > 0)
                            progressList.position.toFloat() / progressList.duration
                        else 0f,
                        source = it.videoSource,
                        thumbnailUrl = it.thumbnailUrl
                    )
                }
            }

            sections = buildList {
                continueLearning?.let {
                    add(HomeSection.ContinueLearningSection(it))
                }

                if (continueWatching.isNotEmpty()) {
                    add(
                        HomeSection.ContinueWatchingSection(
                            videos = continueWatching.map {
                                RecentVideo(
                                    id = it.id,
                                    title = it.title,
                                    duration = it.duration,
                                    progress = it.progress,
                                    source = it.source,
                                    thumbnailUrl = it.thumbnailUrl
                                )
                            }
                        ))
                }

                if (videos.isNotEmpty()) {
                    add(
                        HomeSection.CourseRowSection(
                            title = "Recommended",
                            courses = videos
                        )
                    )
                }
            }

            isLoading = false
        }
    }
}

class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = FirebaseVideoRepository()
        return HomeViewModel(context, repository) as T
    }
}
