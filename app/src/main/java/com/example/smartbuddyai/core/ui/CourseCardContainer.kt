package com.example.smartbuddyai.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.smartbuddyai.data.model.Course
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseCardContainer(
    course: Course,
    modifier: Modifier = Modifier,
    onFocused: () -> Unit,
    onClick: (Course) -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()


    CourseCard(
        title = course.title,
        duration = course.duration,
        progress = course.progress,
        thumbnailUrl = course.thumbnailUrl,
        isFocused = isFocused,
        onFocusChange = {
            isFocused = it
            if (it) {
                onFocused()
                scope.launch {
                    bringIntoViewRequester.bringIntoView()
                }
            }
        },
        modifier = modifier.bringIntoViewRequester(bringIntoViewRequester),
        onClick = { onClick(course) }
    )
}
