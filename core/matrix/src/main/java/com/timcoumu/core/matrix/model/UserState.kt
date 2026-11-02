package com.timcoumu.core.matrix.model

data class UserState(
    val isLoggedIn: Boolean,
    val userId: String,
    val displayName: String,
    val avatarUrl: String
)
