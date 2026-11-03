package com.timcoumu.matrix.feature.room.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.room.presentation.RoomManagerViewModel
import com.timcoumu.matrix.feature.room.ui.RoomManagerScreen

@Composable
fun RoomManagerRoute(
    roomId: String,
    onBack:()-> Unit
){
    val roomManagerViewModel = hiltViewModel<RoomManagerViewModel>()
    val roomUiState by roomManagerViewModel.roomManageUiState.collectAsStateWithLifecycle()
    LaunchedEffect(roomId) {
        roomManagerViewModel.initViewModel()
    }
    RoomManagerScreen(
        roomManagerUiState = roomUiState,
        onBack = onBack,
        onLeaveRoom = {}
    )
}