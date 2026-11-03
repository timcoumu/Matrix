package com.timcoumu.matrix.feature.room.state

import com.timcoumu.core.matrix.model.MyRoomMemberState
import com.timcoumu.core.matrix.model.RoomState

data class RoomManagerUiState(
    val room: RoomState?,
    val members: List<MyRoomMemberState>
)
