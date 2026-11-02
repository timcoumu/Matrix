package com.timcoumu.matrix.ui

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import com.timcoumu.matrix.TopLevelDestination

@Composable
fun MainBottomBar(
    destinations: List<TopLevelDestination>,
    currentDestination: String?,
    onNavigate: (TopLevelDestination) -> Unit
) {
    NavigationBar() {
        destinations.forEach { destination ->
            val currentIcon = when (destination) {
                TopLevelDestination.HOME -> Icons.Outlined.ChatBubbleOutline
                TopLevelDestination.SPACE -> Icons.Outlined.Category
                TopLevelDestination.SETTING -> Icons.Outlined.Settings
                TopLevelDestination.ME -> Icons.Outlined.Person
                else -> Icons.Outlined.Error
            }
            val currentIconFilled = when (destination) {
                TopLevelDestination.HOME -> Icons.Default.Chat
                TopLevelDestination.SPACE -> Icons.Default.Category
                TopLevelDestination.SETTING -> Icons.Default.Settings
                TopLevelDestination.ME -> Icons.Default.Person
                else -> Icons.Default.Error
            }
            val isSelectedRoute = currentDestination == destination.route::class.qualifiedName
            NavigationBarItem(
                selected = isSelectedRoute,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(imageVector = if(isSelectedRoute) currentIconFilled else currentIcon, "")
                }
            )
        }
    }
}