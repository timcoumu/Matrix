package com.timcoumu.matrix.feature.room.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.timcoumu.core.matrix.model.MyRoomMemberState
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.matrix.feature.room.components.CardContainer
import com.timcoumu.matrix.feature.room.components.LeaveRoomButton
import com.timcoumu.matrix.feature.room.components.MatrixBitmapLoader
import com.timcoumu.matrix.feature.room.components.TopBar
import com.timcoumu.matrix.feature.room.state.RoomManagerUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomManagerScreen(
    onBack: () -> Unit,
    onLeaveRoom: () -> Unit,
    onClickMemberList: () -> Unit,
    onClickRoomName: (name: String) -> Unit,
    onClickRoomTopic: (topic: String) -> Unit,
    roomManagerUiState: RoomManagerUiState,
    onClickInvite:()-> Unit
) {
    var leaveRoomDialog by remember { mutableStateOf(false) }
    var editNameDialog by remember { mutableStateOf(false) }
    var editNameValue by remember { mutableStateOf("") }
    var editTopicDialog by remember { mutableStateOf(false) }
    var editTopicValue by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar(
            "房间管理",
            onBack = onBack,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                MatrixBitmapLoader(
                    url = roomManagerUiState.room?.avatarUrl?:"",
                    errorSize = 50.dp
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = roomManagerUiState.room?.displayName ?: "无标题",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = roomManagerUiState.room?.topic ?: "无主题",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        CardContainer(
            modifier = Modifier.combinedClickable(
                onClick = onClickMemberList
            ),
            title = "成员列表"
        ) {
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(
                    items = roomManagerUiState.members.take(4),
                    key = { it.usrId }
                ) {
                    Column(
                        modifier = Modifier.width(80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            MatrixBitmapLoader(
                                url = it.avatarUrl,
                                errorSize = 30.dp
                            )
                        }
                        Text(
                            text = it.displayName,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        }
        CardContainer(
            modifier = Modifier.combinedClickable(
                onClick = {
//                    onClickRoomName(roomManagerUiState.room?.displayName ?: "")
                    editNameDialog = true
                }
            ),
            title = "房间名", titleDesc = roomManagerUiState.room?.displayName ?: "")
        CardContainer(
            modifier = Modifier.combinedClickable(
                onClick = {
//                    onClickRoomTopic(roomManagerUiState.room?.topic ?: "")
                    editTopicDialog = true
                }
            ), title = "主题", titleDesc = roomManagerUiState.room?.topic ?: "")
        Surface(
            onClick = onClickInvite,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "邀请用户",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        LeaveRoomButton({
            leaveRoomDialog = true
        })
    }
    if (leaveRoomDialog) AlertDialog(
        title = { Text("提示") },
        onDismissRequest = {
            leaveRoomDialog = false
        },
        confirmButton = {
            Button(
                onClick = {
                    leaveRoomDialog = false
                    onLeaveRoom()
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("确定")
            }

        },
        dismissButton = {
            Button(
                onClick = {
                    leaveRoomDialog = false
                }
            ) {
                Text("取消")
            }
        },
        text = {
            Text("真的要离开房间吗？")
        }
    )
    if (editNameDialog) AlertDialog(
        title = { Text("房间名") },
        onDismissRequest = {
            editNameDialog = false
        },
        confirmButton = {
            Button(
                onClick = {
                    onClickRoomName(roomManagerUiState.room?.displayName ?: "")
                    editNameDialog = false
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("确定")
            }

        },
        dismissButton = {
            Button(
                onClick = {
                    editNameDialog = false
                }
            ) {
                Text("取消")
            }
        },
        text = {
            TextField(
                value = editNameValue,
                onValueChange = {editNameValue = it}
            )
        }
    )
    if (editTopicDialog) AlertDialog(
        title = { Text("主题") },
        onDismissRequest = {
            editTopicDialog = false
        },
        confirmButton = {
            Button(
                onClick = {
                    onClickRoomTopic(roomManagerUiState.room?.topic ?: "")
                    editTopicDialog = false
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("确定")
            }

        },
        dismissButton = {
            Button(
                onClick = {
                    editTopicDialog = false
                }
            ) {
                Text("取消")
            }
        },
        text = {
            TextField(
                value = editTopicValue,
                onValueChange = {editTopicValue = it}
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewRoomManagerScreen() {
    RoomManagerScreen(
        onBack = {},
        roomManagerUiState = RoomManagerUiState(
            room = RoomState(
                id = "32",
                topic = "聊天的房间",
                displayName = "我的房间1号",
                avatarUrl = ""
            ),
            members = listOf(
                MyRoomMemberState(
                    usrId = "",
                    avatarUrl = "",
                    displayName = "张三",
                    memberRole = "Administrator",
                    memberShip = "Knock"
                )
            )
        ),
        onLeaveRoom = {},
        onClickMemberList = {},
        onClickRoomName = {},
        onClickRoomTopic = {},
        onClickInvite = {}
    )
}