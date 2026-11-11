package com.timcoumu.matrix.feature.user.effect

sealed class ProfileEffect {
    data class ChangeUsername(
        val result: Boolean,
        val message: String
    ): ProfileEffect()
    data class ChangeUserAvatar(
        val result: Boolean,
        val message: String
    ): ProfileEffect()
}