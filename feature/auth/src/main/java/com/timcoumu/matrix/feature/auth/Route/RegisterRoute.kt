package com.timcoumu.matrix.feature.auth.Route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.auth.effect.RegisterEffect
import com.timcoumu.matrix.feature.auth.presentation.RegisterViewModel
import com.timcoumu.matrix.feature.auth.ui.RegisterScreen

@Composable
fun RegisterRoute(
    onBack: () -> Unit,
    onOpenLogin: () -> Unit,
    showMessage:(value:String)-> Unit
) {
    val registerViewModel = hiltViewModel<RegisterViewModel>()
    val registerUiState = registerViewModel.registerUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        registerViewModel.registerEffect.collect {
            when(it){
                is RegisterEffect.Register->{
                    showMessage(it.message)
                    if (it.success) onOpenLogin()
                }
            }
        }
    }
    RegisterScreen(
        registerUiState = registerUiState.value,
        onUsernameChange = registerViewModel::onUsernameChanged,
        onPasswordChange = registerViewModel::onPasswordChanged,
        onConfirmPasswordChange = registerViewModel::onConfirmPasswordChanged,
        onRegisterTokenChange = registerViewModel::onRegisterTokenChanged,
        onBack = onBack,
        onOpenLogin = onOpenLogin,
        onRegister = registerViewModel::onRegister
    )
}