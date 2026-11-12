package com.timcoumu.matrix.feature.chat.ui

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timcoumu.core.matrix.model.MessageState
import com.timcoumu.core.matrix.model.UserState
import com.timcoumu.matrix.feature.chat.components.ChatListItem
import com.timcoumu.matrix.feature.chat.components.ChatTextField
import com.timcoumu.matrix.feature.chat.components.TopBar
import com.timcoumu.matrix.feature.chat.state.ChatUiState

@Composable
fun ChatScreen(
    chatUiState: ChatUiState,
    onChangeSendMessage: (message: String) -> Unit,
    chatScopeCall: (function: suspend () -> Unit) -> Unit,
    onOpenRoomManager:(roomId: String)-> Unit,
    onSend: () -> Unit,
    onBack: () -> Unit
) {
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    // 监听 items 变化，数据就绪后滚动到底部
    LaunchedEffect(chatUiState.messages.size) {
        if (chatUiState.messages.isNotEmpty()) {
            listState.scrollToItem(chatUiState.messages.lastIndex)
        }
    }
    Column(modifier= Modifier.fillMaxSize()) {
        TopBar(
            name = "聊天房间",
            onBack = onBack,
            content = {
                Icon(Icons.Default.Menu,"", modifier = Modifier.combinedClickable(
                    onClick = {
                        onOpenRoomManager(chatUiState.roomId)
                    }

                ))
            }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .imePadding()
                .fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = chatUiState.messages,
                    key = {
                        it.id
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        ChatListItem(
                            sender = it.sender,
                            text = it.body,
                            isSender = it.sender == chatUiState.userState.userId
                        )
//                    Text(it.sender + ":")
//                    Text(it.body, fontWeight = FontWeight.SemiBold)
//                    if (it.isEdited) Text("（已修改）")
//                    Text("消息类型：" + it.msgType)
                    }
                }
            }
            ChatTextField(
                modifier = Modifier,
                onSend = {
                    onSend()
                    chatScopeCall {
                        if (chatUiState.messages.lastIndex != -1)listState.scrollToItem(chatUiState.messages.lastIndex)
                    }
                    keyboardController?.hide()
                },
                text = chatUiState.sendMessage,
                onChangeMessage = onChangeSendMessage
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen(
        onBack = {},
        chatUiState = ChatUiState(
            messages = listOf(
                MessageState(
                    id = "1234",
                    isAvailable = true,
                    sender = "李四",
                    body = "你好",
                    msgType = "String",
                    isEdited = true
                ),
                MessageState(
                    id = "123",
                    isAvailable = true,
                    sender = "张三",
                    body = "你好",
                    msgType = "String",
                    isEdited = true
                )
            ),
            sendMessage = "",
            userState = UserState(
                isLoggedIn = true,
                userId = "张三",
                displayName = "",
                avatarUrl = ""
            ),
            roomId = ""
        ),
        onSend = {},
        onChangeSendMessage = {},
        chatScopeCall = {},
        onOpenRoomManager = {}
    )
}