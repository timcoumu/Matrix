package com.timcoumu.matrix.feature.room.effect

sealed class InviteBoxEffect {
    data class AcceptEffect(
        val result: Boolean,
        val message: String
    ): InviteBoxEffect()
    data class RejectEffect(
        val result: Boolean,
        val message:String
    ): InviteBoxEffect()
}