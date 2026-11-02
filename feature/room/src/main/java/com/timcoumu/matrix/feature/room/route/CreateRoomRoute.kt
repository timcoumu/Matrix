package com.timcoumu.matrix.feature.room.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.timcoumu.core.matrix.model.MyRoomPreset
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.room.effect.CreateRoomEffect
import com.timcoumu.matrix.feature.room.presentation.CreateRoomViewModel
import com.timcoumu.matrix.feature.room.ui.CreateRoomScreen

@Composable
fun CreateRoomRoute(
    onBack: () -> Unit,
    type: RoomType,
    isEditor: Boolean,
    spaceId: String?,
    spaceName: String,
    spaceAvatar: String,
    spaceTopic: String,
    spaceJoinRule: String,
    showMessage: (String) -> Unit
) {
    val createRoomViewModel = hiltViewModel<CreateRoomViewModel>()
    val createRoomUiState by createRoomViewModel.createRoomUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        createRoomViewModel.createRoomEffect.collect { effect ->
            when (effect) {
                is CreateRoomEffect.CreateRoom -> {
                    if (effect.success) {
                        showMessage("创建成功")
                        onBack()
                    } else {
                        showMessage("创建失败 ${effect.message}")
                    }
                }
                is CreateRoomEffect.editEffect -> {
                    showMessage("修改成功")
                    onBack()
                }
            }
        }
    }
    LaunchedEffect(spaceId) {
        spaceId?.let {
            createRoomViewModel.onUpdateRoomId(spaceId)
            createRoomViewModel.onUpdateRoomName(spaceName)
            createRoomViewModel.onUpdateAvatar(spaceAvatar)
            createRoomViewModel.onUpdateTopic(spaceTopic)
            createRoomViewModel.onUpdatePresent(
                when (spaceJoinRule) {
                    "Public" -> MyRoomPreset.PUBLIC_CHAT
                    "Private" -> MyRoomPreset.PRIVATE_CHAT
                    else -> MyRoomPreset.PRIVATE_CHAT
                }
            )
        }
    }
    CreateRoomScreen(
        type = type,
        isEditor = isEditor,
        createRoomUiState = createRoomUiState,
        onUpdateName = createRoomViewModel::onUpdateRoomName,
        onUpdateTopic = createRoomViewModel::onUpdateTopic,
        onUpdatePreset = createRoomViewModel::onUpdatePresent,
        onBack = onBack,
        onCreate = { createRoomViewModel.onCreateRoomOrSapce(type) },
        onSaveEditSpace = createRoomViewModel::onSaveEditSpace,
        onDeleteSpaceOrRoom = {
            createRoomViewModel.onLeaveSpace()
            onBack()
        },
    )
}