package com.timcoumu.matrix.feature.room.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timcoumu.matrix.feature.room.state.CreateRoomUiState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.timcoumu.core.matrix.model.MyRoomPreset
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.room.components.TopBar

@Composable
fun CreateRoomScreen(
    type: RoomType,
    isEditor: Boolean,
    createRoomUiState: CreateRoomUiState,
    onBack:()-> Unit,
    onUpdateName:(name: String)-> Unit,
    onUpdateTopic:(topic: String)-> Unit,
    onUpdatePreset:(preset: MyRoomPreset)-> Unit,
    onCreate:()-> Unit,
    onDeleteSpaceOrRoom:()->Unit,
    onSaveEditSpace:()->Unit,
){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(isEditor){
            TopBar(
                name = "编辑信息",
                onBack = onBack
            )
        }else{
            when(type){
                RoomType.ROOM->{
                    TopBar(
                        name = "创建房间",
                        onBack = onBack
                    )
                }
                RoomType.SPACE->{
                    TopBar(
                        name = "创建空间",
                        onBack = onBack
                    )
                }
            }
        }


        // ---------- 名称 ----------
        OutlinedTextField(
            value = createRoomUiState.name,
            onValueChange = onUpdateName,
            label = { Text("名称") },
            placeholder = { Text("起个名字") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        // ---------- 主题 ----------
        OutlinedTextField(
            value = createRoomUiState.topic,
            onValueChange = onUpdateTopic,
            label = { Text("主题") },
            placeholder = { Text("这个房间聊些什么？") },
            minLines = 3,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        // ---------- 房间 类型（二选一） ----------
        Column(
            modifier = Modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "房间类型",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "公共聊天",
                        fontSize = 20.sp
                    )
                    RadioButton(
                        selected = createRoomUiState.preset is MyRoomPreset.PUBLIC_CHAT,
                        onClick = { onUpdatePreset(MyRoomPreset.PUBLIC_CHAT) }
                    )
                }
                Text("任何人都可以加入")
            }
            Column(){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "私人聊天",
                        fontSize = 20.sp
                    )
                    RadioButton(
                        selected = createRoomUiState.preset is MyRoomPreset.PRIVATE_CHAT,
                        onClick = { onUpdatePreset(MyRoomPreset.PRIVATE_CHAT) }
                    )
                }
                Text("受到邀请后才可以加入")
            }
        }
        if (isEditor){
            Button(
                onClick = onSaveEditSpace,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("保存")
            }
            Button(
                onClick = onDeleteSpaceOrRoom,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("删除")
            }
        }else{
            Button(
                onClick = when(type){
                    RoomType.ROOM->{
                        onCreate
                    }
                    RoomType.SPACE->{
                        onCreate
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                when(type){
                    RoomType.ROOM->{
                        Text("创建房间")
                    }
                    RoomType.SPACE->{
                        Text("创建空间")
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateRoomScreen(){
    MaterialTheme{
        CreateRoomScreen(
            createRoomUiState = CreateRoomUiState(
                avatar = "",
                name = "工作群1",
                topic = "用于工作聊天的群聊",
                preset = MyRoomPreset.PRIVATE_CHAT,
                id = ""
            ),
            onUpdateName = {},
            onUpdateTopic = {},
            onUpdatePreset = {},
            onBack = {},
            type = RoomType.SPACE,
            onCreate = {},
            isEditor = true,
            onSaveEditSpace = {},
            onDeleteSpaceOrRoom = {}
        )
    }
}