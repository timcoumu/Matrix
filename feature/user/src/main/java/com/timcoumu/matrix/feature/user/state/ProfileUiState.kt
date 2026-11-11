package com.timcoumu.matrix.feature.user.state

import com.timcoumu.core.matrix.model.UserState

data class ProfileUiState(
    val user: UserState?,
    val usernameDialog: UsernameDialogState,
    val userAvatarDialog: UserAvatarDialogState
)
data class UsernameDialogState(
    val show: Boolean,
    val username: String
)
data class UserAvatarDialogState(
    val show: Boolean,
    val avatarUrl: String
)