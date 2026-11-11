package com.timcoumu.matrix.feature.user.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.user.effect.ProfileEffect
import com.timcoumu.matrix.feature.user.presentation.ProfileViewModel
import com.timcoumu.matrix.feature.user.ui.ProfileScreen

@Composable
fun ProfileRoute(
    onBack: () -> Unit,
    showMessage: (String) -> Unit,
    userId: String
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val profileUiState by profileViewModel.profileUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        profileViewModel.profileEffect.collect { effect ->
            when(effect){
                is ProfileEffect.ChangeUsername->{
                    showMessage(effect.message)
                }
                is ProfileEffect.ChangeUserAvatar->{
                    showMessage(effect.message)
                }
            }
        }
    }
    LaunchedEffect(userId) {
        profileViewModel.initViewModel(userId)
    }
    ProfileScreen(
        onBack = onBack,
        profileUiState = profileUiState,
        onClickAvatar = profileViewModel::onShowUserAvatarDialog,
        onClickUsername = profileViewModel::onShowUsernameDialog,
        onUsernameChange = profileViewModel::onUsernameChanged,
        onUserAvatarChange = profileViewModel::onUserAvatarChanged,
        onCloseDialog = profileViewModel::onCloseDialog,
        onConfirmUsernameChange = profileViewModel::confirmNameChanged,
        onConfirmUserAvatarChange = profileViewModel::confirmAvatarChanged,
        uploadImage = profileViewModel::uploadImage
    )
}