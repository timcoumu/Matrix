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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String,
    onBack: () -> Unit
) {
    Row(modifier = Modifier.padding(vertical = 10.dp)
        ,verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .combinedClickable(onClick = onBack),
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text,  fontSize = 20.sp)
    }
}