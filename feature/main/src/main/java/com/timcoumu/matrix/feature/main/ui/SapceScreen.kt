package com.timcoumu.matrix.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Workspaces
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timcoumu.core.matrix.model.SpaceState
import com.timcoumu.matrix.feature.main.components.SpaceRow
import com.timcoumu.matrix.feature.main.components.TopBar
import com.timcoumu.matrix.feature.main.state.SpaceUiState

@Composable
fun SpaceScreen(
    spaceUiState: SpaceUiState,
    onClickSpace:(SpaceState)->Unit,
    onLongClickSpace:(spaceId:String,spaceName:String,spaceAvatar:String,spaceTopic:String,spaceJoinRule: String)-> Unit
){
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar("空间")
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .align(Alignment.CenterHorizontally)) {
            Box(modifier = Modifier,
                contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center,
                    ){
                        Icon(Icons.Outlined.Workspaces,"")
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(text = "你创建或加入的空间")
                }

            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                items = spaceUiState.spaces,
                key = {it.id}
            ){
                SpaceRow(
                    displayName = it.displayName,
                    topic = it.topic?:"",
                    avatarUrl = it.avatarUrl?:"",
                    onClick = { onClickSpace(it) },
                    onLongClick = { onLongClickSpace(
                        it.id,it.displayName,it.avatarUrl?:"",it.joinRule,it.joinRule
                    ) }
                ) 
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpaceScreen(){
    SpaceScreen(
        spaceUiState = SpaceUiState(
            spaces = listOf(
                SpaceState(
                    id = "123",
                    displayName = "空间",
                    topic = "聊天专用",
                    avatarUrl = "",
                    joinRule = "invited"
                )
            )
        ),
        onClickSpace = {},
        onLongClickSpace = {
            spaceId, spaceName, spaceAvatar, spaceTopic, spaceJoinRule ->
        }
    )
}