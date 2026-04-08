package com.example.smartbuddyai.feature.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.smartbuddyai.data.model.SidebarItem

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SidebarItemView(
    item: SidebarItem,
    isSelected: Boolean,
    isFocused: Boolean,
    isExpanded: Boolean,
    onFocus: () -> Unit,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.15f else 1f,
        label = ""
    )
    val backgroundColor = when {
        isSelected -> Color(0xFF1DB954)
        isFocused -> Color(0xFF2A2A2A)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .height(56.dp)
            .fillMaxWidth()
            .scale(scale)
            .onFocusChanged {
                if (it.isFocused) onFocus()
            }
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .focusable()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isExpanded) {
            Arrangement.Start
        } else {
            Arrangement.Center
        }
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = Color.White
            )
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.label,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
        }
    }
}