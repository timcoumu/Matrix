package com.timcoumu.matrix.feature.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.Matrix
import com.timcoumu.matrix.feature.main.state.MeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class MeViewModel @Inject constructor() : ViewModel() {
    private val TAG = "MeViewModel"

    //    private val _meUiState = MutableStateFlow(
//        MeUiState(
//            displayName = "",
//            avatarUrl = "",
//            userId = "",
//            userIdServerName = "",
//            isLoggedIn = false
//        )
//    )
//    val meUiState = combine(_meUiState, Matrix.isLoggedIn) { meUiState, isLoggedIn ->
//        Log.d(TAG, "meUiState:${meUiState} ")
//        MeUiState(
//            isLoggedIn = isLoggedIn,
//            displayName = meUiState.displayName,
//            avatarUrl = meUiState.avatarUrl,
//            userId = meUiState.userId,
//            userIdServerName = meUiState.userIdServerName
//        )
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = MeUiState(
//            isLoggedIn = false,
//            displayName = "",
//            avatarUrl = "",
//            userId = "",
//            userIdServerName = ""
//        )
//    )
    val meUiState: StateFlow<MeUiState> = Matrix.userState.map {
        MeUiState(
            isLoggedIn = it.isLoggedIn,
            displayName = it.displayName,
            avatarUrl = it.avatarUrl,
            userId = it.userId,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MeUiState(
            isLoggedIn = false,
            displayName = "",
            avatarUrl = "",
            userId = ""
        )
    )

    fun logout() {
        viewModelScope.launch {
            try {
                Matrix.logout()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "logout: ${e}")
            }
        }
    }

}