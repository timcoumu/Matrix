package com.timcoumu.matrix.feature.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.timcoumu.matrix.feature.main.components.SettingItem
import com.timcoumu.matrix.feature.main.components.SettingsCard
import com.timcoumu.matrix.feature.main.components.TopBar

@Composable
fun SettingScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar("设置")
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

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen(){
    MaterialTheme{
        SettingScreen()
    }
}