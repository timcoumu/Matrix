package com.timcoumu.matrix.feature.room.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.room.route.CreateRoomRoute
import com.timcoumu.matrix.feature.room.route.InviteBoxRoute
import com.timcoumu.matrix.feature.room.route.InviteRoute
import com.timcoumu.matrix.feature.room.route.RoomInSpaceRoute
import com.timcoumu.matrix.feature.room.route.RoomManagerRoute
import kotlinx.serialization.Serializable



@Serializable
data class CreateDestination(
    val spaceId: String? = null,
    val spaceName:String? = "",
    val spaceAvatar:String?="",
    val spaceTopic:String?="",
    val spaceJoinRule: String="",
    val type: RoomType,
    val isEditor: Boolean
)
@Serializable
data class RoomInSpaceDestination(
    val spaceId: String
)
@Serializable
data class RoomManagerDestination(
    val roomId: String
)
@Serializable
data class InviteDestination(
    val roomId: String?
)
@Serializable
object InviteBoxDestination
fun NavGraphBuilder.createScreen(
    onBack:()-> Unit,
    showMessage:(String)-> Unit,
    onLongClick:()-> Unit,
    onOpenChat:(String)-> Unit,
    onOpenHome:()->Unit,
    onOpenInvite:(roomId: String?)-> Unit
){
    composable<CreateDestination>(
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
    ){
        val createDestination = it.toRoute<CreateDestination>()
        CreateRoomRoute(
            onBack = onBack,
            type = createDestination.type,
            showMessage = showMessage,
            isEditor = createDestination.isEditor,
            spaceId = createDestination.spaceId,
            spaceName = createDestination.spaceName?:"",
            spaceAvatar = createDestination.spaceAvatar?:"",
            spaceTopic = createDestination.spaceTopic?:"",
            spaceJoinRule = createDestination.spaceJoinRule
        )
    }
    composable<RoomInSpaceDestination>(
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
    ){
        val roomInSpaceDestination = it.toRoute<RoomInSpaceDestination>()
        RoomInSpaceRoute(
            onBack = onBack,
            onOpenChat = onOpenChat,
            onLongClick = onLongClick,
            spaceId=roomInSpaceDestination.spaceId
        )
    }
    composable<RoomManagerDestination>(
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
    ){
        val roomManagerDestination = it.toRoute<RoomManagerDestination>()
        RoomManagerRoute(
            roomId = roomManagerDestination.roomId,
            onBack = onBack,
            showMessage = showMessage,
            onOpenHome = onOpenHome,
            onOpenInvite = onOpenInvite
        )
    }
    composable<InviteDestination>(
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
    ){
        val inviteDestination = it.toRoute<InviteDestination>()
        InviteRoute(
            onBack = onBack,
            roomId = inviteDestination.roomId,
            showMessage = showMessage
        )
    }
    composable<InviteBoxDestination>(
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
    ){
        InviteBoxRoute(
            onBack = onBack,
            showMessage = showMessage
        )
    }
}