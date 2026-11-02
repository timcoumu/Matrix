package com.timcoumu.matrix.feature.main.state

import com.timcoumu.core.matrix.model.RoomState

data class HomeUiState(
    val rooms: List<RoomState>,
    val isAddDialogVisible: Boolean
)
