package com.timcoumu.matrix.feature.room.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.matrix.feature.room.state.RoomManagerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RoomManagerViewModel @Inject constructor() : ViewModel() {
    private val _currentRoom = MutableStateFlow<RoomState?>(null)
    val roomManageUiState = combine(_currentRoom, MatrixRoomService.roomMembersFlow) { room,members ->
        RoomManagerUiState(
            room = room,
            members = members
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RoomManagerUiState(
            room = null,
            members = emptyList()
        )
    )

    fun initViewModel() {
        viewModelScope.launch {
            val room = MatrixRoomService.getRoomState()
            _currentRoom.value = room
            MatrixRoomService.getRoomMembers()
        }
    }
}