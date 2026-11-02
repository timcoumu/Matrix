package com.timcoumu.matrix.core.network.dto

data class RegisterBody(
    val username:String,
    val password: String,
    val auth: Auth
)
data class Auth(
    val type: String,
    val token:String?
)