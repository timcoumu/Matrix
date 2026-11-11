package com.timcoumu.matrix.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.timcoumu.matrix.feature.main.components.LogoutButton
import com.timcoumu.matrix.feature.main.components.ProfileHeader
import com.timcoumu.matrix.feature.main.components.TopBar
import com.timcoumu.matrix.feature.main.state.MeUiState

@Composable
fun MeScreen(
    meUiState: MeUiState,
    onBack:()->Unit,
    onOpenRegister:()-> Unit,
    onOpenProfile:(userId: String)-> Unit,
    onQrClick:()-> Unit,
    onLogout:()->Unit,
    onOpenLogin:()-> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar("我的")
        ProfileHeader(
            displayName = meUiState.displayName,
            userId = meUiState.userId,
            onOpenProfile = onOpenProfile,
            onQrClick = onQrClick,
            avatarUrl = meUiState.avatarUrl,
            isLoggedIn = meUiState.isLoggedIn,
            onOpenLogin = onOpenLogin
        )
        if (meUiState.isLoggedIn) LogoutButton(onLogout)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen(){
    MeScreen(
        onBack = {},
        onOpenLogin = {},
        onOpenRegister = {},
        onOpenProfile = {},
        onQrClick = {},
        meUiState = MeUiState(
            displayName = "displayname",
            avatarUrl = "url",
            userId = "用户id",
            isLoggedIn = true
        ),
        onLogout = {},
    )
}