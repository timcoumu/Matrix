package com.timcoumu.matrix.feature.room.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.room.effect.InviteBoxEffect
import com.timcoumu.matrix.feature.room.presentation.InviteBoxViewModel
import com.timcoumu.matrix.feature.room.ui.InviteBoxScreen

@Composable
fun InviteBoxRoute(
    onBack:()-> Unit,
    showMessage:(String)-> Unit
){
    val inviteBoxViewModel = hiltViewModel<InviteBoxViewModel>()
    val inviteBoxUiState by inviteBoxViewModel.inviteBoxUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        inviteBoxViewModel.inviteBoxEffect.collect {
            effect->
            when (effect){
                is InviteBoxEffect.AcceptEffect ->{
                    showMessage(effect.message)
                }
                is InviteBoxEffect.RejectEffect->{
                    showMessage(effect.message)
                }
            }
        }
    }
    InviteBoxScreen(
        inviteBoxUiState = inviteBoxUiState,
        onBack = onBack,
        onAccept = inviteBoxViewModel::acceptInvite,
        onReject = inviteBoxViewModel::rejectInvite,
    )
}