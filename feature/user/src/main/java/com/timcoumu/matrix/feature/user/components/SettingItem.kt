package com.timcoumu.matrix.feature.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingItem(modifier: Modifier = Modifier,text: String){
    Row(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
        ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp).background(MaterialTheme.colorScheme.surfaceContainerLowest),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text, style = MaterialTheme.typography.bodyLarge)
            Icon(Icons.Default.ChevronRight,"")
        }
    }
}

@Preview
@Composable
fun PreviewSettingItem(){
    MaterialTheme{
        SettingItem(
            text = "用户头像"
        )
    }
}