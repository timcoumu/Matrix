package com.timcoumu.matrix.feature.main.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.core.matrix.MatrixSpaceService
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.core.matrix.model.SpaceState
import com.timcoumu.matrix.feature.main.presentation.SpaceViewModel
import com.timcoumu.matrix.feature.main.ui.SpaceScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SpaceRoute(
    onBack: () -> Unit,
    onOpenSpace: (SpaceState) -> Unit,
    onOpenEditor: (spaceId: String, spaceName: String, spaceAvatar: String, spaceTopic: String, spaceJoinRule: String, type: RoomType) -> Unit
//    onOpenHome:()->Unit,
//    onOpenSetting:()->Unit,
//    onOpenMe:()->Unit,
) {
    val spaceViewModel = hiltViewModel<SpaceViewModel>()
    val spaceUiState by spaceViewModel.spaceUiState.collectAsStateWithLifecycle()
    SpaceScreen(
        spaceUiState = spaceUiState,
        onClickSpace = onOpenSpace,
        onLongClickSpace = { spaceId, spaceName, spaceAvatar, spaceTopic, spaceJoinRule ->
            onOpenEditor(spaceId, spaceName, spaceAvatar, spaceTopic,spaceJoinRule, RoomType.SPACE)
        }
    )
}