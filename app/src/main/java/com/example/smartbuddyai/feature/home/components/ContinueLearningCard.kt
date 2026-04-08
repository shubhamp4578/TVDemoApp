package com.example.smartbuddyai.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.data.model.ContinueLearning

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ContinueLearningCard(
    data: ContinueLearning,
    onClick: (String) -> Unit
) {
    Card(
        onClick = { onClick(data.videoId) },
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(220.dp)
            .padding(start = 40.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1A2A6C),
                            Color(0xFF0F2027)
                        )
                    )
                )
                .padding(24.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = data.subtitle,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(data.progress.coerceIn(0f, 1f))
                            .height(6.dp)
                            .background(Color(0xFF2196F3))
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFC107), shape = RoundedCornerShape(50))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text("Resume Learning", color = Color.Black)
                }
            }
        }
    }
}