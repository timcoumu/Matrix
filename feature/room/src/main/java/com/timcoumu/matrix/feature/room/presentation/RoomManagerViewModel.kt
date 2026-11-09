package com.timcoumu.matrix.feature.room.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.matrix.feature.room.effect.RoomManageEffect
import com.timcoumu.matrix.feature.room.state.RoomManagerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RoomManagerViewModel @Inject constructor() : ViewModel() {
    private val TAG = "RoomManagerViewModel"
    private val _roomManagerUiEffect = MutableSharedFlow<RoomManageEffect>(replay = 0)
    val roomManagerEffect = _roomManagerUiEffect.asSharedFlow()
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
    fun leaveRoom(){
        viewModelScope.launch {
            try {
                MatrixRoomService.leaveRoom()
                _roomManagerUiEffect.emit(
                    RoomManageEffect.LeaveRoom(
                        result = true,
                        message = "已离开"
                    )
                )
            }catch (e: Exception){
                throw e
            } catch (e: Exception) {
                _roomManagerUiEffect.emit(
                    RoomManageEffect.LeaveRoom(
                        result = false,
                        message = "退出房间失败 ${e.message}"
                    )
                )
                Log.e(TAG, "leaveRoom: ${e}", )
            }
        }
    }
    fun setRoomName(name: String){
        viewModelScope.launch {
            MatrixRoomService.setRoomName(name)
        }
    }
    fun setRoomTopic(topic: String){
        viewModelScope.launch {
            MatrixRoomService.setRoomTopic(topic)
        }
    }
    fun initViewModel() {
        viewModelScope.launch {
            val room = MatrixRoomService.getRoomState()
            _currentRoom.value = room
            MatrixRoomService.getRoomMembers()
        }
    }
}