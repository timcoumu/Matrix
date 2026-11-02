package com.timcoumu.matrix.feature.auth.state

data class RegisterUiState(
    val username:String,
    val password:String,
    val confirmPassword: String,
    val registerToken:String?
)