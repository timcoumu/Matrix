package com.timcoumu.matrix.feature.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    text: String,
    onBack: () -> Unit
) {
    Row(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
        ,verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.padding(horizontal = 16.dp,vertical = 10.dp)) {
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .combinedClickable(onClick = onBack),
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text, style = MaterialTheme.typography.titleMedium)
        }

    }
}