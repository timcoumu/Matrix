package com.timcoumu.matrix.feature.user.components

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SAFButton(callback:(bytes: ByteArray?)-> Unit){
    val context = LocalContext.current
    var bytes: ByteArray? = null
    val resolver = context.contentResolver
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ){
            uri ->
        if (uri != null){
            CoroutineScope(Dispatchers.IO).launch{
                bytes = resolver.openInputStream(uri)?.use {
                    it.readBytes()
                }
                callback(bytes)
            }

        }else{
            callback(null)
        }
    }
    Button(
        onClick = {
            launcher.launch(arrayOf("image/*"))
        }
    ) {
        Text("上传", style = MaterialTheme.typography.labelLarge)
    }
}

