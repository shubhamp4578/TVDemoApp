package com.example.smartbuddyai.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.core.ui.CourseCardContainer
import com.example.smartbuddyai.data.model.Course
import com.example.smartbuddyai.data.model.RecentVideo

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ContinueWatchingRow(
    items: List<RecentVideo>,
    onItemClick: (String) -> Unit
) {
    Column {
        Text(
            text = "Continue Watching",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { item ->

                CourseCardContainer(
                    course = Course(
                        id = item.id,
                        title = item.title,
                        duration = item.duration,
                        videoSource = item.source,
                        thumbnailUrl = item.thumbnailUrl,
                        progress = item.progress
                    ),
                    onFocused = {},
                    onClick = { course ->
                        onItemClick(course.id)
                    }
                )
            }
        }
    }
}