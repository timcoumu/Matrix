package com.timcoumu.matrix.feature.room.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixRoomService
import com.timcoumu.core.matrix.MatrixSpaceService
import com.timcoumu.core.matrix.model.MyRoomPreset
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.room.effect.CreateRoomEffect
import com.timcoumu.matrix.feature.room.state.CreateRoomUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class CreateRoomViewModel @Inject constructor(): ViewModel() {
    private val TAG = "CreateRoomViewModel"
    private val _createRoomEffect = MutableSharedFlow<CreateRoomEffect>(replay = 0)
    private val _createRoomUiState = MutableStateFlow<CreateRoomUiState>(
        CreateRoomUiState(
            id="",
            avatar = "",
            name = "",
            topic = "",
            preset = MyRoomPreset.PRIVATE_CHAT
        )
    )
    val createRoomEffect = _createRoomEffect.asSharedFlow()
    val createRoomUiState = _createRoomUiState.asStateFlow()
    fun onUpdateRoomId(id: String){
        _createRoomUiState.update {
            it.copy(
                id = id
            )
        }
    }
    fun onUpdateRoomName(name: String){
        _createRoomUiState.update {
            it.copy(
                name=name
            )
        }
    }
    fun onUpdateTopic(topic: String){
        _createRoomUiState.update {
            it.copy(
                topic = topic
            )
        }
    }
    fun onUpdateAvatar(avatar:String){
        _createRoomUiState.update {
            it.copy(
                avatar=avatar
            )
        }
    }
    fun onUpdatePresent(preset: MyRoomPreset){
        _createRoomUiState.update {
            it.copy(
                preset = preset
            )
        }
    }
    fun onCreateRoomOrSapce(
        type: RoomType
    ){
        viewModelScope.launch {
            try {
                MatrixRoomService.createRoomOrSpace(
                    name = _createRoomUiState.value.name,
                    topic = _createRoomUiState.value.topic,
                    preset = _createRoomUiState.value.preset,
                    type = type
                )
                _createRoomEffect.emit(
                    CreateRoomEffect.CreateRoom(
                        success = true,
                        message = null
                    )
                )
            }catch (e: CancellationException){
                throw e
            }
            catch (e: Exception){
                _createRoomEffect.emit(
                    CreateRoomEffect.CreateRoom(
                        success = false,
                        message = e.message
                    )
                )
                Log.e(TAG, "onCreateRoom: ${e}")
            }

        }
    }
    fun onSaveEditSpace(){
        viewModelScope.launch {
            val state = _createRoomUiState.value
            MatrixSpaceService.editSpace(
                id=state.id,
                topic = state.topic,
                name = state.name,
                avatar = state.avatar,
                joinRule = when(state.preset){
                    MyRoomPreset.PUBLIC_CHAT -> "Public"
                    MyRoomPreset.PRIVATE_CHAT -> "Private"
                }
            )
            _createRoomEffect.emit(
                CreateRoomEffect.editEffect(
                    success = true,
                    message = "修改成功"
                )
            )
        }
    }
    fun onLeaveSpace(){
        viewModelScope.launch {
            MatrixSpaceService.leaveSpace(_createRoomUiState.value.id)
        }
    }
}