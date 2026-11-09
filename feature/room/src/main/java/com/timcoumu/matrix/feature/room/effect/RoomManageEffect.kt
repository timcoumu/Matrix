package com.timcoumu.matrix.feature.room.effect

sealed class RoomManageEffect{
    data class LeaveRoom(
        val result: Boolean,
        val message: String
    ): RoomManageEffect()
}
