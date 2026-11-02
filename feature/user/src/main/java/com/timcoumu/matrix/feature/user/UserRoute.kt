package com.timcoumu.matrix.feature.user

import androidx.compose.runtime.Composable
import com.timcoumu.matrix.feature.user.ui.UserScreen

@Composable
fun UserRoute(
    onBack:()->Unit
){
    UserScreen(
        onBack = onBack
    )
}