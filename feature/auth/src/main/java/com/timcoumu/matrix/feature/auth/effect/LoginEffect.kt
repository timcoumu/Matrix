package com.timcoumu.matrix.feature.auth.effect

sealed class LoginEffect {
    data class Login(
        val success: Boolean,
        val message: String?
    ): LoginEffect()
}