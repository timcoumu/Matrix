package com.timcoumu.matrix.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timcoumu.matrix.feature.auth.components.TopBar
import com.timcoumu.matrix.feature.auth.state.RegisterUiState

@Composable
fun RegisterScreen(
    registerUiState: RegisterUiState,
    onUsernameChange:(value: String)->Unit,
    onPasswordChange:(value: String)->Unit,
    onConfirmPasswordChange:(value: String)->Unit,
    onRegisterTokenChange:(value: String)-> Unit,
    onRegister:()-> Unit,
    onBack:()->Unit,
    onOpenLogin: () -> Unit
){
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        TopBar(
            onBack = onBack,
            text = "登录"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = registerUiState.username,
                onValueChange = onUsernameChange,
                label = { Text("用户名") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = registerUiState.password,
                onValueChange = onPasswordChange,
                label = { Text("密码") },
                singleLine = true,
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(if (passwordVisible) "Hide" else "Show")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = registerUiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("再次输入密码") },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    TextButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Text(if (confirmPasswordVisible) "Hide" else "Show")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = registerUiState.registerToken?:"",
                onValueChange = onRegisterTokenChange,
                label = { Text("用户名") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("注册")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onOpenLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("已有账号，去登陆")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen(){
    RegisterScreen(
        registerUiState = RegisterUiState(
            username = "",
            password = "",
            confirmPassword = "",
            registerToken = null
        ),
        onUsernameChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegister = {},
        onBack = {},
        onOpenLogin = {},
        onRegisterTokenChange = {}
    )
}