package com.timcoumu.matrix.feature.room.state

import com.timcoumu.core.matrix.model.MyRoomPreset

data class CreateRoomUiState(
    val id: String,
    val avatar:String,
    val name:String,
    val topic:String,
    val preset: MyRoomPreset
)
