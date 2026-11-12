package com.timcoumu.matrix.feature.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timcoumu.matrix.feature.main.components.SettingItem
import com.timcoumu.matrix.feature.main.components.SettingsCard
import com.timcoumu.matrix.feature.main.components.TopBar

@Composable
fun SettingScreen(
    onOpenInviteBox:()->Unit,
){
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar("设置")
        Column(modifier = Modifier.padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SettingsCard {
                SettingItem(
                    icon = Icons.Outlined.Inbox,
                    iconTint = Color(138, 43, 226),
                    title = "邀请",
                    trailing = null,
                    onClick = onOpenInviteBox
                )
            }
            SettingsCard {
                SettingItem(
                    icon = Icons.Outlined.Info,
                    iconTint = Color(0, 206, 209),
                    title = "关于",
                    trailing = null,
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen(){
    MaterialTheme{
        SettingScreen(
            onOpenInviteBox = {}
        )
    }
}