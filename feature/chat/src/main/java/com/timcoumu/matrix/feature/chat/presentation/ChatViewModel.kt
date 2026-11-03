package com.timcoumu.matrix.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.Matrix
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.core.matrix.model.UserState
import com.timcoumu.matrix.feature.chat.state.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {
    private val TAG = "ChatViewModel"
    private val _roomId = MutableStateFlow<String>("")
    private val _sendMessage = MutableStateFlow("")
    val chatUiState = combine(
        MatrixRoomService.messageFlowTemp,
        _sendMessage,
        Matrix.userState,
                _roomId
    ){messageTemp,sendMessage,userState,roomId->
        ChatUiState(
            messages = messageTemp,
            sendMessage = sendMessage,
            userState = userState,
            roomId = roomId,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ChatUiState(
            messages = emptyList(),
            sendMessage = "",
            userState = UserState(
                isLoggedIn = false,
                userId = "",
                displayName = "",
                avatarUrl = ""
            ),
            roomId = ""
        )
    )
    fun initChatViewModel(roomId: String){
        viewModelScope.launch {
            _roomId.value = roomId
            MatrixRoomService.registerTimelineForOneRoom(roomId)
        }
    }
    fun onChangeSendMessage(value: String){
        _sendMessage.value = value
    }
    fun onSend(roomId: String){
        viewModelScope.launch {
            if(chatUiState.value.sendMessage.isNotBlank()) {
                MatrixRoomService.sendMessage(roomId, chatUiState.value.sendMessage)
                _sendMessage.value = ""
            }
        }
    }
    fun chatScopeCall(
        function:suspend ()-> Unit
    ){
        viewModelScope.launch {
            function()
        }
    }
}