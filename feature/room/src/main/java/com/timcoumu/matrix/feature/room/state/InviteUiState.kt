package com.timcoumu.matrix.feature.room.state

import com.timcoumu.core.matrix.model.UserState

data class InviteUiState(
    val roomId:String?,
    val username:String,
    val userList: List<UserState>,
    val confirmDialogState: ConfirmDialogState
)

data class ConfirmDialogState(
    val showDialog: Boolean,
    val user: UserState?
)