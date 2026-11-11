package com.timcoumu.matrix.feature.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingCard(title: String, content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding( top = 10.dp)) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }

    }
}

@Preview
@Composable
fun PreviewSettingCard() {
    MaterialTheme {
        SettingCard(
            title = "个人信息",
            content = {
                SettingItem(text = "用户设置")
            }
        )
    }
}