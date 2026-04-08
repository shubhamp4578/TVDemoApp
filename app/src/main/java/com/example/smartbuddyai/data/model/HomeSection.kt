package com.example.smartbuddyai.data.model

sealed class HomeSection {
    data class ContinueLearningSection(
        val data: ContinueLearning
    ) : HomeSection()

    data class CourseRowSection(
        val title: String,
        val courses: List<Course>
    ) : HomeSection()

    data class ContinueWatchingSection(
        val videos: List<RecentVideo>
    ) : HomeSection()
}