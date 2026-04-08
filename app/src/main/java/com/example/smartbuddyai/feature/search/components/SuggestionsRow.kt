package com.example.smartbuddyai.feature.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SuggestionsRow(
    suggestions: List<String>,
    onClick: (String) -> Unit,
    focusRequester: FocusRequester,
    onDown: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .focusRequester(focusRequester),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 32.dp, end = 32.dp)
    ) {
        items(suggestions) { suggestion ->
            var isFocused by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .onFocusChanged { isFocused = it.isFocused }
                    .scale(if (isFocused) 1.1f else 1f)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isFocused)
                            Color(0xFFeec200).copy(alpha = 0.25f)
                        else
                            Color(0xFF1A2236)
                    )
                    .shadow(
                        elevation = if (isFocused) 12.dp else 0.dp,
                        shape = RoundedCornerShape(50)
                    )
                    .clickable { onClick(suggestion) }
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .onPreviewKeyEvent {
                        if (it.type == KeyEventType.KeyDown && it.key == Key.DirectionDown) {
                            onDown()
                            true
                        } else false
                    }
            ) {
                Text(
                    text = suggestion,
                    color = if (isFocused)
                        Color(0xFFeec200)
                    else
                        Color(0xFFd0d8ff)
                )
            }
        }
    }
}