package com.timcoumu.matrix.feature.room.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.room.effect.InviteEffect
import com.timcoumu.matrix.feature.room.presentation.InviteViewModel
import com.timcoumu.matrix.feature.room.ui.InviteScreen

@Composable
fun InviteRoute(
    onBack:()-> Unit,
    roomId: String?,
    showMessage:(String)->Unit
){
    val inviteViewModel = hiltViewModel<InviteViewModel>()
    val inviteUiState by inviteViewModel.inviteUiState.collectAsStateWithLifecycle()
    LaunchedEffect(roomId) {
        inviteViewModel.initInviteViewModel(roomId)
    }
    LaunchedEffect(Unit) {
        inviteViewModel.inviteEffect.collect {
            effect->
            when(effect){
                is InviteEffect.InviteUser->{
                    effect.message?.let { showMessage(it) }
                }
            }
        }
    }
    InviteScreen(
        onBack = onBack,
        inviteUiState = inviteUiState,
        onSearchUsers = inviteViewModel::onSearchUsers,
        onUsernameChang = inviteViewModel::onChangeUsername,
        onShowDialog = inviteViewModel::onShowDialog,
        onConfirmDialog = inviteViewModel::inviteUser,
        onDismissDialog = inviteViewModel::onDismissDialog
    )
}