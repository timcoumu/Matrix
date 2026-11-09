package com.timcoumu.core.matrix.model

import org.matrix.rustcomponents.sdk.UserProfile

data class UserState(
    val isLoggedIn: Boolean,
    val userId: String,
    val displayName: String,
    val avatarUrl: String
)
fun UserProfile.toUserState():UserState{
    return UserState(
        isLoggedIn = false,
        userId = this.userId,
        displayName = displayName?:"",
        avatarUrl = avatarUrl?:""
    )
}