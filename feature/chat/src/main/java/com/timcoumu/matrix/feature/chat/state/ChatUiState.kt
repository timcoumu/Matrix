package com.timcoumu.matrix.feature.chat.state

import com.timcoumu.core.matrix.model.MessageState
import com.timcoumu.core.matrix.model.UserState

data class ChatUiState(
    val messages: List<MessageState>,
    val sendMessage: String,
    val userState: UserState
)
