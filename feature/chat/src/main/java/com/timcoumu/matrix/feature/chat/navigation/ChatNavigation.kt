package com.timcoumu.matrix.feature.chat.navigation

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
    onBack:()-> Unit
){
    composable<ChatDestination> {
        val chatDestination = it.toRoute<ChatDestination>()
        ChatRoute(
            onBack = onBack,
            roomId = chatDestination.roomId
        )
    }
}