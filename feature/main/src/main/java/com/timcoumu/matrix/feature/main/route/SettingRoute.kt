package com.timcoumu.matrix.feature.main.route

import androidx.compose.runtime.Composable
import com.timcoumu.matrix.feature.main.ui.SettingScreen

@Composable
fun SettingRoute(
    onBack:()-> Unit,
    onOpenInviteBox:()-> Unit
//    onOpenHome:()->Unit,
//    onOpenSpace:()->Unit,
//    onOpenMe:()->Unit,
){
    SettingScreen(
        onOpenInviteBox = onOpenInviteBox
    )
}