package com.timcoumu.matrix.feature.room.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.timcoumu.core.matrix.untils.Tools

@Composable
fun MatrixBitmapLoader(url: String,modifier: Modifier = Modifier,errorSize: Dp){
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(url) {
        bitmap = Tools.getImage(url)
    }
    if (bitmap == null){
        AsyncImage(
            modifier = modifier.size(errorSize),
            model = null,
            contentDescription = "",
            error = rememberVectorPainter(Icons.Default.Person)
        )
    }else{
        AsyncImage(
            modifier = modifier.fillMaxSize(),
            model = bitmap,
            contentDescription = "",
            error = rememberVectorPainter(Icons.Default.Person)
        )
    }
}