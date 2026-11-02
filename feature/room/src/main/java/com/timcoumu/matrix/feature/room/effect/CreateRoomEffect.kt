package com.timcoumu.matrix.feature.room.effect

sealed class CreateRoomEffect{
    data class CreateRoom(
        val success: Boolean,
        val message: String?
    ): CreateRoomEffect()
    data class editEffect(
        val success: Boolean,
        val message: String?
    ): CreateRoomEffect()
}
