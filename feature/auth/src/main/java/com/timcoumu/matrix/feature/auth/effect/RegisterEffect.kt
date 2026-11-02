package com.timcoumu.matrix.feature.auth.effect

sealed class RegisterEffect(){
    data class Register(
        val success: Boolean,
        val message: String
    ): RegisterEffect()
}
