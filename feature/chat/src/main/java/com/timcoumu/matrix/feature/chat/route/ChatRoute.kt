package com.timcoumu.matrix.feature.chat.route

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.chat.presentation.ChatViewModel
import com.timcoumu.matrix.feature.chat.ui.ChatScreen


@Composable
fun ChatRoute(
    roomId: String,
    onBack:()->Unit
){
    val TAG = "ChatRoute"
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val chatUiState by chatViewModel.chatUiState.collectAsStateWithLifecycle()
    LaunchedEffect(roomId) {
        Log.d(TAG, "ChatRoute: 进入房间：${roomId}")
        chatViewModel.registerTimeline(roomId)
    }
    ChatScreen(
        onBack = onBack,
        chatUiState = chatUiState,
        onSend = { chatViewModel.onSend(roomId) },
        onChangeSendMessage = chatViewModel::onChangeSendMessage,
        chatScopeCall = { chatViewModel.chatScopeCall(it) },
    )
}