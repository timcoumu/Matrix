package com.timcoumu.matrix.feature.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    name: String,
    content:@Composable ()-> Unit = {}
) {
    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceContainerLow)
        .padding(horizontal = 16.dp, vertical = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(
            name,
            style = MaterialTheme.typography.titleLarge
        )
        content()
    }
}

@Preview
@Composable
fun PreviewTopBar(){
    MaterialTheme{
        TopBar("首页")
    }
}