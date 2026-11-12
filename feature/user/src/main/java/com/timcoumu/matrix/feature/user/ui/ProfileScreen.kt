package com.timcoumu.matrix.feature.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timcoumu.core.matrix.model.UserState
import com.timcoumu.matrix.feature.user.components.SAFButton
import com.timcoumu.matrix.feature.user.components.SettingCard
import com.timcoumu.matrix.feature.user.components.SettingItem
import com.timcoumu.matrix.feature.user.components.TopBar
import com.timcoumu.matrix.feature.user.state.ProfileUiState
import com.timcoumu.matrix.feature.user.state.UserAvatarDialogState
import com.timcoumu.matrix.feature.user.state.UsernameDialogState

@Composable
fun ProfileScreen(
    onBack:()-> Unit,
    onClickAvatar:()-> Unit,
    onClickUsername:()-> Unit,
    profileUiState: ProfileUiState,
    onUsernameChange:(String)-> Unit,
    onUserAvatarChange:(String)-> Unit,
    uploadImage:(bytes: ByteArray?)-> Unit,
    onCloseDialog:()-> Unit,
    onConfirmUsernameChange:()-> Unit,
    onConfirmUserAvatarChange:()-> Unit
){
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            text = "用户设置",
            onBack = onBack
        )
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp)) {
            SettingCard(
                title = "用户信息",
            ){
                SettingItem(
                    modifier = Modifier.combinedClickable(
                        onClick = onClickUsername
                    ),
                    text = "用户名"
                )
                SettingItem(
                    modifier = Modifier.combinedClickable(
                        onClick = onClickAvatar
                    ),
                    text = "头像"
                )
            }
        }

    }
    if (profileUiState.usernameDialog.show) AlertDialog(
        title = {
            Text("修改用户名", style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            TextField(
                value = profileUiState.usernameDialog.username,
                onValueChange = onUsernameChange,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        },
        onDismissRequest = {
            onCloseDialog()
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmUsernameChange()
                    onCloseDialog()
                }
            ) {
                Text("确定", style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onCloseDialog()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("取消", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
    if (profileUiState.userAvatarDialog.show) AlertDialog(
        title = {
            Text("修改头像", style = MaterialTheme.typography.headlineSmall)
        },
        text = {},
        onDismissRequest = {
            onCloseDialog()
        },
        confirmButton = {
            SAFButton(
                callback = {
                    uploadImage(it)
                    onCloseDialog()
                }
            )
//            Button(onClick = {
//                onConfirmUserAvatarChange()
//                onCloseDialog()
//            }) {
//                Text("确定", style = MaterialTheme.typography.labelLarge)
//            }

        },
        dismissButton = {
            Button(
                onClick = {
                    onCloseDialog()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("取消", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}

@Preview
@Composable
fun PreviewProfileScreen(){
    MaterialTheme{
        ProfileScreen(
            onBack = {},
            profileUiState = ProfileUiState(
                user = UserState(
                    isLoggedIn = true,
                    userId = "1234",
                    displayName = "Liliy",
                    avatarUrl = "dsdsdsd"
                ),
                usernameDialog = UsernameDialogState(
                    show = false,
                    username = ""
                ),
                userAvatarDialog = UserAvatarDialogState(
                    show = false,
                    avatarUrl = ""
                )
            ),
            onClickAvatar = {},
            onClickUsername = {},
            onUsernameChange = {},
            onUserAvatarChange = {},
            onCloseDialog = {},
            onConfirmUsernameChange = {},
            onConfirmUserAvatarChange = {},
            uploadImage = {}
        )
    }
}