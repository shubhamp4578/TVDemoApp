package com.example.smartbuddyai.feature.search

import com.example.smartbuddyai.data.model.Course

data class SearchUiState(
    val query: String = "",
    val suggestions: List<String> = emptyList(),
    val results: List<Course> = emptyList()
)
