package com.timcoumu.matrix.feature.main.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.main.presentation.MeViewModel
import com.timcoumu.matrix.feature.main.ui.MeScreen

@Composable
fun MeRoute(
    onBack:()->Unit,
    onOpenLogin:()-> Unit,
    onOpenRegister:()-> Unit,
    onOpenProfile:(userId:String)-> Unit
){
    val meViewModel = hiltViewModel<MeViewModel>()
    val meUiState by meViewModel.meUiState.collectAsStateWithLifecycle()
    MeScreen(
        meUiState = meUiState,
        onBack = onBack,
        onOpenLogin = onOpenLogin,
        onOpenRegister = onOpenRegister,
        onOpenProfile = onOpenProfile,
        onQrClick = {},
        onLogout = meViewModel::logout
    )
}