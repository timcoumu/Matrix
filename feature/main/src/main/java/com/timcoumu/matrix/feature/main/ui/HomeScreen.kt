package com.timcoumu.matrix.feature.main.ui

import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Workspaces
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.timcoumu.core.matrix.model.RoomState
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.main.components.ConversationRow
import com.timcoumu.matrix.feature.main.components.TopBar
import com.timcoumu.matrix.feature.main.state.HomeUiState

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onOpenChat:(roomId: String)-> Unit,
    onShowAdd:()-> Unit,
    onHideAdd:()-> Unit,
    onLongClickRoom:()-> Unit,
    onOpenCreate:(type: RoomType)->Unit,
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            name = "首页",
            content = { Icon(
                modifier = Modifier.combinedClickable(
                    onClick = onShowAdd
                ),
                imageVector = Icons.Default.Add,
                contentDescription = "") }
            )
        LazyColumn() {
            items(
                items = homeUiState.rooms,
                key = {it.id}
            ){
                ConversationRow(
                    roomId=it.id,
                    displayName = it.displayName?:"",
                    topic = it.topic?:"",
                    avatarUrl = it.avatarUrl?:"",
                    onOpenChat = onOpenChat,
                    onLongClick = onLongClickRoom
                )
            }
        }
    }
    if (homeUiState.isAddDialogVisible) Dialog(
        onDismissRequest = onHideAdd,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
        )
    ) {
        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
        LaunchedEffect(dialogWindowProvider) {
            dialogWindowProvider?.window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                // 全屏后 gravity 其实无意义，但留着也无害
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onHideAdd
                ),
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(
                modifier = Modifier
                    .padding(top = 8.dp, end = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    ),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 6.dp
            ) {
                Column(modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    Row(
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                onHideAdd()
                                onOpenCreate(RoomType.ROOM)
                                }
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(Icons.Outlined.ChatBubbleOutline,"",
                            Modifier.size(18.dp))
                        Text("新建聊天", style = MaterialTheme.typography.bodyLarge)
                    }
                    HorizontalDivider(modifier = Modifier.width(100.dp))
                    Row(
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                onHideAdd()
                                onOpenCreate(RoomType.SPACE)
                            }
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(Icons.Outlined.Workspaces,"",
                            Modifier.size(18.dp))
                        Text("新建空间", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen(){
    MaterialTheme() {
        HomeScreen(
            homeUiState = HomeUiState(
                rooms = listOf(
                    RoomState(
                        id = "1",
                        topic = "房间1的主题",
                        displayName = "房间1",
                        avatarUrl = ""
                    ),
                    RoomState(
                        id = "2",
                        topic = "房间2的主题",
                        displayName = "房间2",
                        avatarUrl = ""
                    ),
                    RoomState(
                        id = "3",
                        topic = "房间3的主题",
                        displayName = "房间3",
                        avatarUrl = ""
                    )
                ),
                isAddDialogVisible = true
            ),
            onOpenChat = {},
            onLongClickRoom = {},
            onShowAdd = {},
            onHideAdd = {},
            onOpenCreate = {}
        )
    }

}