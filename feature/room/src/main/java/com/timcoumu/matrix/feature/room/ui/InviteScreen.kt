package com.timcoumu.matrix.feature.room.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.timcoumu.core.matrix.model.UserState
import com.timcoumu.matrix.feature.room.components.InviteUserRow
import com.timcoumu.matrix.feature.room.components.TopBar
import com.timcoumu.matrix.feature.room.state.ConfirmDialogState
import com.timcoumu.matrix.feature.room.state.InviteUiState

@Composable
fun InviteScreen(
    onBack:()-> Unit,
    inviteUiState : InviteUiState,
    onSearchUsers:()-> Unit,
    onUsernameChang:(username: String)->Unit,
    onShowDialog:(userState:UserState)-> Unit,
    onConfirmDialog:()->Unit,
    onDismissDialog:()->Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    fun search() {
        if (inviteUiState.username.isBlank()) return
        onSearchUsers()
        keyboardController?.hide()
        focusManager.clearFocus()
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar(
            name = "邀请用户",
            onBack = onBack
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inviteUiState.username,
                onValueChange = onUsernameChang,
                label = { Text("用户名") },
                placeholder = { Text("搜索要邀请的用户") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "")
                },
                trailingIcon = {
                    if (inviteUiState.username.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUsernameChang("")
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "清除")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { search() }),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { search() },
                enabled = inviteUiState.username.isNotBlank()
            ) {
                Text("搜索")
            }
        }
        if (inviteUiState.userList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (inviteUiState.username.isBlank()) {
                        "输入用户名搜索要邀请的用户"
                    } else {
                        "没有找到用户"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(
                    items = inviteUiState.userList,
                    key = { it.userId }
                ) {
                    InviteUserRow(modifier = Modifier.combinedClickable(
                        onClick = {
                            onShowDialog(it)
                        }
                    ),user = it)
                }
            }
        }
    }
    if (inviteUiState.confirmDialogState.showDialog) AlertDialog(
        title = {Text("提示")},
        onDismissRequest = {onDismissDialog()},
        text = {
            Text("确定邀请${inviteUiState.confirmDialogState.user?.displayName}？")
        },
        dismissButton = {
            Button(onClick = {
                onConfirmDialog()
                onDismissDialog()
            }) {
                Text("确定")
            }
        },
        confirmButton = {
            Button(onClick = {
                onDismissDialog()
            },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("取消")
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun PreviewInviteScreen(){
    MaterialTheme{
        InviteScreen(
            onBack = {},
            onSearchUsers = {},
            onUsernameChang = {},
            inviteUiState = InviteUiState(
                roomId = "",
                username = "张",
                userList = listOf(
                    UserState(
                        isLoggedIn = true,
                        userId = "@zhangsan:matrix.org",
                        displayName = "张三",
                        avatarUrl = ""
                    ),
                    UserState(
                        isLoggedIn = true,
                        userId = "@lisi:matrix.org",
                        displayName = "李四",
                        avatarUrl = ""
                    )
                ),
                confirmDialogState = ConfirmDialogState(
                    false, UserState(
                        isLoggedIn = true,
                        userId = "@zhangsan:matrix.org",
                        displayName = "张三",
                        avatarUrl = ""
                    )
                )
            ),
            onShowDialog = {},
            onConfirmDialog = {},
            onDismissDialog = {}
        )
    }
}
