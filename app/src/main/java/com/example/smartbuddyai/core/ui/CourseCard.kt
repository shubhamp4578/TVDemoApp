package com.example.smartbuddyai.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CourseCard(
    title: String,
    duration: String,
    progress: Float,
    thumbnailUrl: String,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.05f else 1f,
        label = ""
    )
    Card(
        modifier = modifier
            .padding(end = 24.dp)
            .size(width = 260.dp, height = 150.dp)
            .padding(bottom = 8.dp)
            .scale(scale)
            .graphicsLayer(clip = false)
            .onFocusChanged { onFocusChange(it.isFocused) }
            .shadow(
                elevation = if (isFocused) 30.dp else 0.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0xFFEEC200).copy(alpha = 0.4f),
                spotColor = Color(0xFFEEC200).copy(alpha = 0.4f)
            ),
        border = CardDefaults.border(
            border = Border(
                BorderStroke(
                    width = if (isFocused) 2.dp else 0.dp,
                    color = Color.White
                )
            )
        ),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xCC000000)
                            )
                        )
                    )
            ) {
                Text(
                    title,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    maxLines = 1
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        duration,
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
                    )

                    if (progress > 0f) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(Color.DarkGray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                                    .height(4.dp)
                                    .background(Color(0xFF2196F3))
                            )
                        }
                    }
                }

            }
        }
    }
}