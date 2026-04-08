package com.example.smartbuddyai.feature.quiz.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text

@Composable
fun QuizOption(
    text: String,
    isFocused: Boolean,
    isSelected: Boolean,
    isCorrect: Boolean,
    showResult: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.05f else 1f,
        label = ""
    )

    val backgroundColor = when {
        showResult && isCorrect -> Color(0xFF4CAF50)
        showResult && isSelected && !isCorrect -> Color(0xFFF44336)
        isFocused -> Color(0xFF2A2A2A)
        else -> Color(0xFF1A1A1A)

    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(20.dp)
            .focusable()
            .onKeyEvent {
                if (it.type == KeyEventType.KeyDown &&
                    (it.key == Key.Enter || it.key == Key.DirectionCenter)
                ) {
                    if (!showResult) onClick()
                    true
                } else false
            }
            .clickable{
                if (!showResult) onClick()
            }
    ) {
        Text(text = text, color = Color.White)
    }
}