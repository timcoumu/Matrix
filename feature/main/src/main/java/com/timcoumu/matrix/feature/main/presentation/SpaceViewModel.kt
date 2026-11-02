package com.timcoumu.matrix.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.MatrixService
import com.timcoumu.core.matrix.MatrixSpaceService
import com.timcoumu.matrix.feature.main.state.HomeUiState
import com.timcoumu.matrix.feature.main.state.SpaceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(): ViewModel() {
    val spaceUiState = combine(MatrixSpaceService.spacesFlow){
        spaceFlow->
        SpaceUiState(
            spaces = spaceFlow[0]
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SpaceUiState(
            spaces = emptyList()
        )
    )
    fun leaveSpace(spaceId:String){
        viewModelScope.launch {
            MatrixSpaceService.leaveSpace(spaceId)
        }
    }
}