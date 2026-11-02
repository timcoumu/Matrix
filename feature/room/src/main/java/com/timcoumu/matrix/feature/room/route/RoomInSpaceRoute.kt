package com.timcoumu.matrix.feature.room.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.room.presentation.RoomInSpaceViewModel
import com.timcoumu.matrix.feature.room.ui.RoomInSpaceScreen

@Composable
fun RoomInSpaceRoute(
    onBack:()-> Unit,
    onOpenChat:(String)-> Unit,
    onLongClick:()-> Unit,
    spaceId: String
){
    val roomInSpaceViewModel = hiltViewModel<RoomInSpaceViewModel>()
    val roomInSpaceUiState by roomInSpaceViewModel.roomInSpaceUiState.collectAsStateWithLifecycle()
    LaunchedEffect(spaceId) {
        roomInSpaceViewModel.loadSpace(spaceId)
    }
    RoomInSpaceScreen(
        roomInSpaceUiState = roomInSpaceUiState,
        onBack = onBack,
        onOpenChat = onOpenChat,
        onLongClick = onLongClick
    )
}