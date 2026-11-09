package com.timcoumu.matrix.feature.room.effect

sealed class InviteEffect{
    data class InviteUser(
        val result: Boolean,
        val message: String?
    ):InviteEffect()
}
