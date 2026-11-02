package com.timcoumu.matrix.feature.room.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixSpaceService
import com.timcoumu.core.matrix.model.SpaceState
import com.timcoumu.matrix.feature.room.state.RoomInSpaceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RoomInSpaceViewModel @Inject constructor(): ViewModel() {
    private val _spaceState = MutableStateFlow(EMPTY_SPACE)
    private var loadedSpaceId: String? = null
    val roomInSpaceUiState = combine(_spaceState, MatrixSpaceService.roomInSelectedSpace){
        spaceState,roomListState->
        RoomInSpaceUiState(
            space = spaceState,
            roomListState = roomListState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RoomInSpaceUiState(
            space = EMPTY_SPACE,
            roomListState = emptyList()
        )
    )
    fun loadSpace(spaceId: String){
        viewModelScope.launch {
            _spaceState.value = MatrixSpaceService.getSpace(spaceId)?:EMPTY_SPACE
            MatrixSpaceService.roomInSelectedSpace.value = emptyList()
            MatrixSpaceService.getSpaceRoomList(spaceId)
        }
    }

    private companion object{
        val EMPTY_SPACE = SpaceState(
            id = "",
            displayName = "",
            topic = "",
            avatarUrl = "",
            joinRule = ""
        )
    }
}