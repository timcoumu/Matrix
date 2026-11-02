package com.timcoumu.matrix.feature.main.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.main.presentation.HomeViewModel
import com.timcoumu.matrix.feature.main.ui.HomeScreen

@Composable
fun HomeRoute(
    onBack:()-> Unit,
    onOpenChat:(roomId: String)->Unit,
    onOpenCreate:(room: RoomType)-> Unit
//    onOpenSpace:()->Unit,
//    onOpenSetting:()->Unit,
//    onOpenMe:()->Unit,
){
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { }
    HomeScreen(
        homeUiState = homeUiState,
        onOpenChat = onOpenChat,
        onLongClickRoom = {},
        onShowAdd = homeViewModel::onShowAddDialogVisible,
        onHideAdd = homeViewModel::onHideAddDialogVisible,
        onOpenCreate = onOpenCreate
    )
}