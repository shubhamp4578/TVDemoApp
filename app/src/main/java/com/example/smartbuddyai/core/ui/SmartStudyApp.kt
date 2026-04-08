package com.example.smartbuddyai.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartbuddyai.core.navigation.AppNavHost
import com.example.smartbuddyai.core.navigation.Screen
import com.example.smartbuddyai.data.model.SidebarItem

@Composable
fun SmartStudyApp() {
    val navController = rememberNavController()

    val items = listOf(
        SidebarItem(Screen.Home, icon = Icons.Default.Home, "Home"),
        SidebarItem(Screen.Search, icon = Icons.Default.Search, "Search"),
        SidebarItem(Screen.Player, icon = Icons.Default.PlayArrow, "Player")
    )
    var focusedIndex by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1220))
    ) {
        Sidebar(
            items = items,
            selectedRoute = currentRoute,
            focusedIndex = focusedIndex,
            onItemFocused = { focusedIndex = it },
            onItemSelected = { _, screen ->
                navController.navigate(screen.route)
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clipToBounds()
        ) {
            AppNavHost(navController)
        }
    }
}