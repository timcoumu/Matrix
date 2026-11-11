package com.timcoumu.matrix.feature.user.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timcoumu.core.matrix.Matrix
import com.timcoumu.matrix.feature.user.effect.ProfileEffect
import com.timcoumu.matrix.feature.user.state.ProfileUiState
import com.timcoumu.matrix.feature.user.state.UserAvatarDialogState
import com.timcoumu.matrix.feature.user.state.UsernameDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val TAG = "ProfileViewModel"
    private val _profileEffect = MutableSharedFlow<ProfileEffect>(replay = 0)
    val profileEffect = _profileEffect.asSharedFlow()
    private val _usernameDialog = MutableStateFlow(
        UsernameDialogState(
            show = false,
            username = ""
        )
    )
    private val _userAvatarDialog = MutableStateFlow(
        UserAvatarDialogState(
            show = false,
            avatarUrl = "",
        )
    )
    val profileUiState = combine(
        Matrix.userState,
        _usernameDialog,
        _userAvatarDialog
    ) { user, username, userAvatar ->
        ProfileUiState(
            user = user,
            usernameDialog = username,
            userAvatarDialog = userAvatar
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState(
            user = null,
            usernameDialog = UsernameDialogState(
                show = false,
                username = ""
            ),
            userAvatarDialog = UserAvatarDialogState(
                show = false,
                avatarUrl = ""
            )
        )
    )

    fun onUsernameChanged(username: String) {
        _usernameDialog.update {
            it.copy(
                username = username
            )
        }
    }

    fun onUserAvatarChanged(avatar: String) {
        _usernameDialog.update {
            it.copy(
                username = avatar
            )
        }
    }

    fun onShowUsernameDialog() {
        _usernameDialog.update {
            it.copy(
                show = true
            )
        }
    }

    fun onShowUserAvatarDialog() {
        _userAvatarDialog.update {
            it.copy(
                show = true
            )
        }
    }

    fun onCloseDialog() {
        _usernameDialog.update {
            it.copy(
                username = "",
                show = false
            )
        }
        _userAvatarDialog.update {
            it.copy(
                avatarUrl = "",
                show = false
            )
        }
    }

    fun confirmNameChanged() {
        viewModelScope.launch {
            try {
                if (_usernameDialog.value.username.isNullOrBlank()) throw Exception("用户名为空")
                Matrix.setUsername(_usernameDialog.value.username)
                _profileEffect.emit(ProfileEffect.ChangeUsername(
                    result = true,
                    message = "修改成功"
                ))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "confirmNameChanged: ${e}")
                _profileEffect.emit(ProfileEffect.ChangeUsername(
                    result = false,
                    message = "修改失败 ${e.message}"
                ))
            }
        }
    }

    fun confirmAvatarChanged() {
//        viewModelScope.launch {
//            try {
//                if (!_userAvatarDialog.value.avatarUrl.startsWith("mxc://")) throw Exception("头像格式有误")
//                Matrix.setAvatarUrl(_userAvatarDialog.value.avatarUrl)
//                _profileEffect.emit(ProfileEffect.ChangeUserAvatar(
//                    result = true,
//                    message = "修改成功"
//                ))
//            } catch (e: CancellationException) {
//                throw e
//            } catch (e: Exception) {
//                Log.e(TAG, "confirmAvatarChanged: ${e}")
//                _profileEffect.emit(ProfileEffect.ChangeUserAvatar(
//                    result = false,
//                    message = "修改失败 ${e.message}"
//                ))
//            }
//        }
    }
    fun uploadImage(bytes: ByteArray?){
        viewModelScope.launch {
            try {
                if (bytes == null){throw Exception("文件读取为空") }
                Matrix.uploadAvatar(bytes)
                _profileEffect.emit(ProfileEffect.ChangeUserAvatar(
                    result = true,
                    message = "修改成功"
                ))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "confirmAvatarChanged: ${e}")
                _profileEffect.emit(ProfileEffect.ChangeUserAvatar(
                    result = false,
                    message = "修改失败 ${e.message}"
                ))
            }
        }
    }

    fun initViewModel(userId: String) {
        onCloseDialog()
    }
}