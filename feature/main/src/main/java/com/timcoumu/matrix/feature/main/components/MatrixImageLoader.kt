package com.timcoumu.matrix.feature.main.components

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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.timcoumu.core.matrix.untils.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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