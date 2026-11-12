package com.timcoumu.matrix.feature.chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ChatListItem(
    sender: String,
    text: String,
    isSender: Boolean,
){
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            contentAlignment = if(isSender) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Text(
                text = sender,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = if(isSender) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            ChatMessage(text)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewChatListItem(){
    MaterialTheme() {
        ChatListItem(
            text = "我是机器人",
            isSender = false,
            sender = "张三"
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewChatListItem_2(){
    MaterialTheme() {
        ChatListItem(
            text = "我也是机器人",
            isSender = true,
            sender = "李四"
        )
    }
}