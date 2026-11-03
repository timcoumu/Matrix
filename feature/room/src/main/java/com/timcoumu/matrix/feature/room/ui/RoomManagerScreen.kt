package com.timcoumu.matrix.feature.room.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.timcoumu.core.matrix.model.MyRoomMemberState
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.matrix.feature.room.components.CardContainer
import com.timcoumu.matrix.feature.room.components.LeaveRoomButton
import com.timcoumu.matrix.feature.room.components.TopBar
import com.timcoumu.matrix.feature.room.state.RoomManagerUiState

@Composable
fun RoomManagerScreen(
    onBack: () -> Unit,
    onLeaveRoom:()->Unit,
    roomManagerUiState: RoomManagerUiState
) {
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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp),
                    model = "",
                    error = rememberVectorPainter(Icons.Default.Person),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = roomManagerUiState.room?.displayName ?: "无标题",
                    fontSize = 24.sp,
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
        CardContainer(title = "成员列表") {
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
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(40.dp),
                                model = "",
                                error = rememberVectorPainter(Icons.Default.Person),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
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
        CardContainer(title = "房间名", titleDesc = roomManagerUiState.room?.displayName?:"")
        CardContainer(title = "主题", titleDesc = roomManagerUiState.room?.topic?:"")
        LeaveRoomButton(onLeaveRoom)
    }
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
        onLeaveRoom = {}
    )
}