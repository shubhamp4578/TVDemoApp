package com.example.smartbuddyai.feature.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.data.model.UserGreeting

@Composable
fun GreetingSection(greeting: UserGreeting) {
    Text(
        text = "Hi ${greeting.name} ",
        style = MaterialTheme.typography.headlineLarge,
        color = Color.White
    )
}