package com.timcoumu.matrix.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.timcoumu.matrix.TopLevelDestination

private val BarShape = RoundedCornerShape(32.dp)
private val ItemShape = RoundedCornerShape(26.dp)

@Composable
fun MainBottomBar(
    destinations: List<TopLevelDestination>,
    currentDestination: String?,
    onNavigate: (TopLevelDestination) -> Unit
) {
    val isLight = MaterialTheme.colorScheme.surface.luminance() > 0.5f
    val glassTop = if (isLight) {
        Color.White.copy(alpha = 0.62f)
    } else {
        Color.White.copy(alpha = 0.14f)
    }
    val glassBottom = if (isLight) {
        Color.White.copy(alpha = 0.24f)
    } else {
        Color.White.copy(alpha = 0.04f)
    }
    val borderBrush = Brush.linearGradient(
        listOf(
            Color.White.copy(alpha = if (isLight) 0.85f else 0.45f),
            Color.White.copy(alpha = 0.05f),
            Color.White.copy(alpha = if (isLight) 0.45f else 0.18f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = BarShape,
                    clip = false,
                    ambientColor = Color.Black.copy(alpha = 0.18f),
                    spotColor = Color.Black.copy(alpha = 0.22f)
                )
                .clip(BarShape)
                .background(Brush.verticalGradient(listOf(glassTop, glassBottom)))
                .border(1.dp, borderBrush, BarShape)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = if (isLight) 0.35f else 0.2f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.12f, 0f),
                            radius = size.width * 0.75f
                        )
                    )
                }
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination ->
                GlassBarItem(
                    destination = destination,
                    selected = currentDestination == destination.route::class.qualifiedName,
                    onClick = { onNavigate(destination) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
@Composable
private fun GlassBarItem(
    destination: TopLevelDestination,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedFraction by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        label = "glassBarItem"
    )
    val itemBackground = Brush.verticalGradient(
        listOf(
            Color.Gray.copy(alpha = 0.5f * selectedFraction),
            Color.Gray.copy(alpha = 0.12f * selectedFraction)
        )
    )
    val itemBorder = Brush.linearGradient(
        listOf(
            Color.White.copy(alpha = 0.75f * selectedFraction),
            Color.White.copy(alpha = 0.1f * selectedFraction)
        )
    )
    val icon = if (selected) destination.filledIcon else destination.outlinedIcon
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(ItemShape)
            .background(itemBackground)
            .border(1.dp, itemBorder, ItemShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.padding(top = 4.dp),
                imageVector = icon,
                contentDescription = destination.label,
                tint = contentColor
            )
            Text(
                text = destination.label,
                color = contentColor,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

private val TopLevelDestination.label: String
    get() = when (this) {
        TopLevelDestination.HOME -> "首页"
        TopLevelDestination.SPACE -> "空间"
        TopLevelDestination.SETTING -> "设置"
        TopLevelDestination.ME -> "我的"
    }

private val TopLevelDestination.outlinedIcon: ImageVector
    get() = when (this) {
        TopLevelDestination.HOME -> Icons.Outlined.ChatBubbleOutline
        TopLevelDestination.SPACE -> Icons.Outlined.Category
        TopLevelDestination.SETTING -> Icons.Outlined.Settings
        TopLevelDestination.ME -> Icons.Outlined.Person
    }

private val TopLevelDestination.filledIcon: ImageVector
    get() = when (this) {
        TopLevelDestination.HOME -> Icons.Default.Chat
        TopLevelDestination.SPACE -> Icons.Default.Category
        TopLevelDestination.SETTING -> Icons.Default.Settings
        TopLevelDestination.ME -> Icons.Default.Person
    }