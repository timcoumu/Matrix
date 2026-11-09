package com.timcoumu.matrix.feature.room.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.room.effect.RoomManageEffect
import com.timcoumu.matrix.feature.room.presentation.RoomManagerViewModel
import com.timcoumu.matrix.feature.room.ui.RoomManagerScreen

@Composable
fun RoomManagerRoute(
    roomId: String,
    onOpenHome:()->Unit,
    showMessage:(msg: String)->Unit,
    onBack:()-> Unit
){
    val roomManagerViewModel = hiltViewModel<RoomManagerViewModel>()
    val roomUiState by roomManagerViewModel.roomManageUiState.collectAsStateWithLifecycle()
    LaunchedEffect(roomId) {
        roomManagerViewModel.initViewModel()
    }
    LaunchedEffect(Unit) {
        roomManagerViewModel.roomManagerEffect.collect {
            when(it){
                is RoomManageEffect.LeaveRoom -> {
                    showMessage(it.message)
                    if (it.result){
                        onOpenHome()
                    }else{

                    }
                }
            }
        }
    }
    RoomManagerScreen(
        roomManagerUiState = roomUiState,
        onBack = onBack,
        onLeaveRoom = roomManagerViewModel::leaveRoom,
        onClickMemberList = {},
        onClickRoomName = roomManagerViewModel::setRoomName,
        onClickRoomTopic = roomManagerViewModel::setRoomTopic
    )
}