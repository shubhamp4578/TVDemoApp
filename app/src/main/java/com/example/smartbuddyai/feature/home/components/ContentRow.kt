package com.example.smartbuddyai.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.core.ui.CourseCardContainer
import com.example.smartbuddyai.data.model.Course

@Composable
fun ContentRow(
    title: String,
    items: List<Course>,
    onItemClick:(String) -> Unit
) {

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.height(200.dp),
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(items) { _, item ->
                CourseCardContainer(
                    course = item,
                    onFocused = {},
                    onClick = {course ->
                        onItemClick(course.id)
                    }
                )
            }
        }
    }
}