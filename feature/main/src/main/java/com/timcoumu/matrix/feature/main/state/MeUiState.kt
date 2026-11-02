package com.timcoumu.matrix.feature.main.state

data class MeUiState(
    val isLoggedIn: Boolean,
    val displayName:String,
    val avatarUrl:String,
    val userId:String,
)
