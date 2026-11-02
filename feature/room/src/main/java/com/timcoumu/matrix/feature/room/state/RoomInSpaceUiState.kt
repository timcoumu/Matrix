package com.timcoumu.matrix.feature.room.state

import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.core.matrix.model.SpaceState

data class RoomInSpaceUiState(
    val space: SpaceState,
    val roomListState: List<RoomState>
)
