package com.example.smartbuddyai.core.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartbuddyai.core.navigation.Screen
import com.example.smartbuddyai.feature.home.components.SidebarItemView
import com.example.smartbuddyai.data.model.SidebarItem

@Composable
fun Sidebar(
    items: List<SidebarItem>,
    selectedRoute: String?,
    focusedIndex: Int,
    onItemFocused: (Int) -> Unit,
    onItemSelected: (Int, Screen) -> Unit
) {
    var isSidebarFocused by remember { mutableStateOf(false) }
    val isExpanded = isSidebarFocused

    val width = animateDpAsState(
        targetValue = if (isExpanded) 240.dp else 100.dp,
        label = ""
    )
    Column(
        modifier = Modifier
            .width(width.value)
            .fillMaxHeight()
            .background(Color(0xFF0B1220))
            .onFocusChanged {
                isSidebarFocused = it.hasFocus
            }
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items.forEachIndexed { index, item ->
            SidebarItemView(
                item = item,
                isSelected = selectedRoute == item.screen.route,
                isFocused = focusedIndex == index,
                isExpanded = isExpanded,
                onFocus = { onItemFocused(index) },
                onClick = { onItemSelected(index, item.screen) }

            )
        }
    }
}