package com.example.smartbuddyai.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbuddyai.core.data.repository.FirebaseVideoRepository
import com.example.smartbuddyai.data.model.Course
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    var state by mutableStateOf(SearchUiState())
        private set
    private val repository = FirebaseVideoRepository()
    private var searchJob: Job? = null
    private var allCourses: List<Course> = emptyList()
    var lastFocusedIndex by mutableIntStateOf(0)
        private set


    fun updateFocusedIndex(index: Int) {
        lastFocusedIndex = index
    }

    init {
        loadVideos()
    }

    init {
        viewModelScope.launch {
            val videos = repository.getVideos()

            state = state.copy(
                results = videos,
                suggestions = videos.map { it.title }
            )
        }
    }

    private fun loadVideos() {
        viewModelScope.launch {
            val videos = repository.getVideos()

            allCourses = videos
            state = state.copy(
                results = videos,
                suggestions = videos.map { it.title }.take(6)
            )
        }
    }

    fun onQueryChange(query: String) {
        state = state.copy(query = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            val result = if (query.isEmpty()) {
                allCourses
            } else {
                allCourses.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
            state = state.copy(
                results = result,
                suggestions = getSuggestions(query)
            )
        }

    }

    fun onSuggestionClick(suggestion: String) {
        onQueryChange(suggestion)
    }

    private fun getSuggestions(query: String): List<String> {
        if (query.isEmpty()) {
            return allCourses.map { it.title }.take(6)
        }
        return allCourses
            .map { it.title }
            .filter { it.contains(query, ignoreCase = true) }
    }
}