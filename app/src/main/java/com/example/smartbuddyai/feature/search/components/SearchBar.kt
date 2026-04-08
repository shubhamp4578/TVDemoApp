package com.example.smartbuddyai.feature.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.LocalTextStyle
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onDown: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = LocalTextStyle.current.copy(
            color = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .onPreviewKeyEvent {
                if (it.key == Key.DirectionDown) {
                    onDown()
                    true
                } else false
            }
            .scale(if (isFocused) 1.02f else 1f)
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFF1A2236),
                        Color(0xFF1F2A40)
                    )
                )
            )
            .padding(horizontal = 28.dp, vertical = 20.dp)
            .shadow(
                elevation = if (isFocused) 20.dp else 0.dp,
                shape = RoundedCornerShape(50)
            ),
        decorationBox = {innerTextField ->
            if (query.isEmpty()) {
                Text(
                    text = "Search Courses...",
                    color = Color.Gray
                )
            }
            innerTextField()
        }
    )
}