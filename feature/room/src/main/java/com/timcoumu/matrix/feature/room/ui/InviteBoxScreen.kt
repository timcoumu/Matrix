package com.timcoumu.matrix.feature.room.ui

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.matrix.feature.room.components.MatrixBitmapLoader
import com.timcoumu.matrix.feature.room.components.TopBar
import com.timcoumu.matrix.feature.room.state.InviteBoxUiState

@Composable
fun InviteBoxScreen(
    inviteBoxUiState: InviteBoxUiState,
    onBack:()-> Unit,
    onAccept:(roomId: String)-> Unit,
    onReject:(roomId: String)-> Unit,
){
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            name = "邀请",
            onBack = onBack
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (inviteBoxUiState.inviteList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无邀请",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = inviteBoxUiState.inviteList,
                        key = { it.id }
                    ) {
                        InviteBoxItem(
                            room = it,
                            onAccept = onAccept,
                            onReject = onReject
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun InviteBoxItem(
    room: RoomState,
    onAccept:(roomId: String)-> Unit,
    onReject:(roomId: String)-> Unit
){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    MatrixBitmapLoader(
                        url = room.avatarUrl?:"",
                        errorSize = 26.dp
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = room.displayName?.takeIf { it.isNotBlank() } ?: "未命名房间",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    room.topic?.takeIf { it.isNotBlank() }?.let {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { onReject(room.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("拒绝", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = { onAccept(room.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("接受", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInviteBoxScreen(){
    MaterialTheme{
        InviteBoxScreen(
            inviteBoxUiState = InviteBoxUiState(
                inviteList = listOf(
                    RoomState(
                        id = "1",
                        topic = "一起来聊聊工作",
                        displayName = "工作群",
                        avatarUrl = ""
                    ),
                    RoomState(
                        id = "2",
                        topic = "周末出去玩",
                        displayName = "朋友群",
                        avatarUrl = ""
                    )
                )
            ),
            onBack = {},
            onAccept = {},
            onReject = {}
        )
    }
}