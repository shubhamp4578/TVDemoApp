package com.example.smartbuddyai.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.example.smartbuddyai.data.model.HomeSection
import com.example.smartbuddyai.feature.home.components.ContentRow
import com.example.smartbuddyai.feature.home.components.ContinueLearningCard
import com.example.smartbuddyai.feature.home.components.ContinueWatchingRow
import com.example.smartbuddyai.feature.home.components.GreetingSection

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onVideoClick: (String) -> Unit
) {
    val greeting = viewModel.greeting
    val sections = viewModel.sections
    val initialFocusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        initialFocusRequester.requestFocus()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 48.dp, top = 32.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        item {
            GreetingSection(greeting)
        }
        items(sections) { section ->
            when (section) {
                is HomeSection.ContinueLearningSection -> {
                    ContinueLearningCard(
                        section.data,
                        onClick = {
                            onVideoClick(section.data.videoId)
                        }
                    )
                }

                is HomeSection.ContinueWatchingSection -> {
                    ContinueWatchingRow(
                        items = section.videos,
                        onItemClick = onVideoClick
                    )
                }

                is HomeSection.CourseRowSection -> {
                    ContentRow(
                        title = section.title,
                        items = section.courses,
                        onItemClick = { videoId ->
                            onVideoClick(videoId)
                        }
                    )
                }
            }
        }
    }
}