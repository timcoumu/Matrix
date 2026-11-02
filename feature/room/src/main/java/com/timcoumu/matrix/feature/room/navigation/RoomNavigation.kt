package com.timcoumu.matrix.feature.room.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.room.route.CreateRoomRoute
import com.timcoumu.matrix.feature.room.route.RoomInSpaceRoute
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


fun NavGraphBuilder.createScreen(
    onBack:()-> Unit,
    showMessage:(String)-> Unit,
    onLongClick:()-> Unit,
    onOpenChat:(String)-> Unit
){
    composable<CreateDestination>{
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
    composable<RoomInSpaceDestination> {
        val roomInSpaceDestination = it.toRoute<RoomInSpaceDestination>()
        RoomInSpaceRoute(
            onBack = onBack,
            onOpenChat = onOpenChat,
            onLongClick = onLongClick,
            spaceId=roomInSpaceDestination.spaceId
        )
    }
}