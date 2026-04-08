package com.example.smartbuddyai.feature.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    onRetry: () -> Unit,
    onContinue: () -> Unit
) {
    val percentage = (score * 100) / total
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }
    val infiniteTransition = rememberInfiniteTransition()

    val glow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val retryFocus = remember { FocusRequester() }
    val continueFocus = remember { FocusRequester() }

    var retryFocused by remember { mutableStateOf(false) }
    var continueFocused by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        continueFocus.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 60.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF0B1220),
                        Color(0xFF0E1A2B),
                        Color(0xFF0B1220)
                    )
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.displayLarge,
                color = Color(0xFFEEC200),
                modifier = Modifier
                    .graphicsLayer {
                        alpha = 0.3f + glow
                        scaleX = 1.1f
                        scaleY = 1.1f
                    }
            )

            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.displayLarge,
                color = Color(0xFFEEC200)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "You scored $score / $total",
            color = Color.LightGray,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(40.dp))
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(tween(800))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .focusRequester(retryFocus)
                        .onFocusChanged { retryFocused = it.isFocused }
                        .background(
                            if (retryFocused) Color(0xFFEEC200) else Color(0xFF1A1A1A),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 32.dp, vertical = 14.dp)
                        .focusable()
                        .onKeyEvent {
                            if (it.type == KeyEventType.KeyDown &&
                                (it.key == Key.Enter || it.key == Key.DirectionCenter)
                            ) {
                                onRetry()
                                true
                            } else false
                        }
                ) {
                    Text(
                        text = "Retry",
                        color = if (retryFocused) Color.Black else Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .focusRequester(continueFocus)
                        .onFocusChanged { continueFocused = it.isFocused }
                        .background(
                            if (continueFocused) Color(0xFFEEC200) else Color(0xFF1A1A1A),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 32.dp, vertical = 14.dp)
                        .focusable()
                        .onKeyEvent {
                            if (it.type == KeyEventType.KeyDown &&
                                (it.key == Key.Enter || it.key == Key.DirectionCenter)
                            ) {
                                onContinue()
                                true
                            } else false
                        }) {
                    Text(
                        text = "Continue Learning",
                        color = if (continueFocused) Color.Black else Color.White
                    )
                }
            }
        }
    }
}