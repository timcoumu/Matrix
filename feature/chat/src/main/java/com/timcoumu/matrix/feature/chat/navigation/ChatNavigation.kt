package com.timcoumu.matrix.feature.chat.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.timcoumu.matrix.feature.chat.route.ChatRoute
import kotlinx.serialization.Serializable

@Serializable
data class ChatDestination(
    val roomId: String
)

fun NavGraphBuilder.chatScreen(
    onBack:()-> Unit,
    onOpenRoomManager:(roomId: String)-> Unit
){
    composable<ChatDestination>(
        enterTransition = {
            slideInHorizontally(tween(300)) { it }
                          },
        exitTransition={
            slideOutHorizontally(tween(300)) { -it }
        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it }
        },
        popExitTransition = {
            slideOutHorizontally(tween(300)) { it }
        }
    ) {
        val chatDestination = it.toRoute<ChatDestination>()
        ChatRoute(
            onBack = onBack,
            roomId = chatDestination.roomId,
            onOpenRoomManager = onOpenRoomManager
        )
    }
}