package com.timcoumu.matrix.feature.auth.Route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.matrix.feature.auth.effect.LoginEffect
import com.timcoumu.matrix.feature.auth.presentation.LoginViewModel
import com.timcoumu.matrix.feature.auth.ui.LoginScreen

@Composable
fun LoginRoute(
    onBack:()-> Unit,
    onLoggedIn:()-> Unit,
    onOpenRegister:()-> Unit,
    showMessage:(value: String)->Unit
){
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loginEffect.collect {
            when(it){
                is LoginEffect.Login -> {
                    if (it.success){
                        showMessage("登陆成功")
                        onLoggedIn()
                    }else{
                        showMessage("登陆失败 ${it.message}")
                    }
                }
            }
        }
    }
    LoginScreen(
        onBack = onBack,
        onOpenRegister = onOpenRegister,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLogin = viewModel::login,
        uiState = uiState
    )
}