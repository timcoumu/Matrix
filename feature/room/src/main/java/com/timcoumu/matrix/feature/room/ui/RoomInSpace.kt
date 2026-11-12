package com.timcoumu.matrix.feature.room.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.core.matrix.model.SpaceState
import com.timcoumu.matrix.feature.room.components.ConversationRow
import com.timcoumu.matrix.feature.room.components.TopBar
import com.timcoumu.matrix.feature.room.state.RoomInSpaceUiState


@Composable
fun RoomInSpaceScreen(
    roomInSpaceUiState: RoomInSpaceUiState,
    onBack: () -> Unit,
    onOpenChat: (String) -> Unit,
    onLongClick: () -> Unit
) {
    val space = roomInSpaceUiState.space
    val rooms = roomInSpaceUiState.roomListState
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            name = space.displayName,
            onBack = onBack
        )
        LazyColumn() {
            items(
                items = rooms,
                key = { it.id }
            ) {
                ConversationRow(
                    roomId = it.id,
                    displayName = it.displayName ?: "",
                    topic = it.topic ?: "",
                    avatarUrl = it.avatarUrl ?: "",
                    onOpenChat = onOpenChat,
                    onLongClick = onLongClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRoomInSpace() {
    MaterialTheme {
        RoomInSpaceScreen(
            roomInSpaceUiState = RoomInSpaceUiState(
                space = SpaceState(
                    id = "21",
                    displayName = "空间1",
                    topic = "空间1的主题",
                    avatarUrl = "",
                    joinRule = ""
                ),
                roomListState = listOf(
                    RoomState(
                        id = "1",
                        topic = "房间1的主题",
                        displayName = "房间1",
                        avatarUrl = ""
                    ),
                    RoomState(
                        id = "2",
                        topic = "房间2的主题",
                        displayName = "房间2",
                        avatarUrl = ""
                    ),
                    RoomState(
                        id = "3",
                        topic = "房间3的主题",
                        displayName = "房间3",
                        avatarUrl = ""
                    )
                )
            ),
            onBack = {},
            onOpenChat = {},
            onLongClick = {}
        )
    }
}