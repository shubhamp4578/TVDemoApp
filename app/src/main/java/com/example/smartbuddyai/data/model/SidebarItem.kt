package com.example.smartbuddyai.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartbuddyai.core.navigation.Screen

data class SidebarItem (
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)