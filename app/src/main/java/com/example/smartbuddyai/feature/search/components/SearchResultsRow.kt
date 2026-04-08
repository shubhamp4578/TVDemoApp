package com.example.smartbuddyai.feature.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.core.ui.CourseCardContainer
import com.example.smartbuddyai.data.model.Course

@Composable
fun SearchResultsRow(
    items: List<Course>,
    lastFocusedIndex: Int,
    onItemFocused: (Int) -> Unit,
    onItemClicked: (String) -> Unit,
    focusRequesters: List<FocusRequester>
) {
    LaunchedEffect(lastFocusedIndex) {
        if (focusRequesters.isNotEmpty() && lastFocusedIndex < focusRequesters.size) {
            focusRequesters[lastFocusedIndex].requestFocus()
        }
    }

    Column {
        Text(
            text = "Results",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.height(180.dp),
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items) { index, item ->
                val requester = focusRequesters.getOrNull(index)

                CourseCardContainer(
                    course = item,
                    modifier = if (requester != null)
                        Modifier.focusRequester(requester)
                    else Modifier,
                    onFocused = { onItemFocused(index) },
                    onClick = { onItemClicked(it.id) }
                )

            }
        }
    }
}