package com.timcoumu.matrix.feature.room.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    name: String,
    onBack:()->Unit,
    content:@Composable ()-> Unit = {}
){
    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainerLow),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.padding(vertical = 10.dp).padding(horizontal = 16.dp),verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.KeyboardArrowLeft,"",
                modifier = Modifier.combinedClickable(onClick = onBack))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                name,
                style = MaterialTheme.typography.titleMedium
            )
        }
        content()
    }
}