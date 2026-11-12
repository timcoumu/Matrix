package com.timcoumu.core.matrix

import android.util.Log
import com.timcoumu.core.data.AppDataStore
import com.timcoumu.core.matrix.model.UserState
import com.timcoumu.matrix.core.network.NetworkClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.matrix.rustcomponents.sdk.Client
import org.matrix.rustcomponents.sdk.ClientBuilder
import org.matrix.rustcomponents.sdk.CrossProcessLockConfig
import org.matrix.rustcomponents.sdk.PresenceState
import org.matrix.rustcomponents.sdk.ProfileListener
import org.matrix.rustcomponents.sdk.Session
import org.matrix.rustcomponents.sdk.SlidingSyncVersion
import org.matrix.rustcomponents.sdk.SlidingSyncVersionBuilder
import org.matrix.rustcomponents.sdk.TaskHandle
import org.matrix.rustcomponents.sdk.UserProfile
import java.io.File

object Matrix {
    val TAG = "Matrix"
    lateinit var client: Client;
    lateinit var appDataStore: AppDataStore
    lateinit var dataPath: String
    lateinit var cachePath: String
    private var profileHandle: TaskHandle? = null
    val userState = MutableStateFlow(
        UserState(
            isLoggedIn = false,
            userId = "",
            displayName = "",
            avatarUrl = ""
        )
    )
    suspend fun initClient(initDataPath: String, initCachePath: String, dataStore: AppDataStore) {
        dataPath = initDataPath
        cachePath = initCachePath
        appDataStore = dataStore
        client = ClientBuilder()
            .homeserverUrl(NetworkClient.BASE_URL)
            .sessionPaths(dataPath = dataPath, cachePath = cachePath)
            .setSessionDelegate(AppSessionDelegate(dataStore = dataStore))
            .slidingSyncVersionBuilder(SlidingSyncVersionBuilder.NATIVE)
            .build()
        val appSession = dataStore.loadSession()
        val cacheSession = Session(
            accessToken = appSession.accessToken,
            refreshToken = appSession.refreshToken.let { it.ifBlank { null } },
            userId = appSession.userId,
            deviceId = appSession.deviceId,
            homeserverUrl = appSession.homeserverUrl,
            oauthData = appSession.oauthData.let { it.ifBlank { null } },
            slidingSyncVersion = if (appSession.slidingSyncVersion == "NATIVE") SlidingSyncVersion.NATIVE else SlidingSyncVersion.NONE
        )
        try {
            client.restoreSession(cacheSession)
//            MatrixService.createRoomServices()
            MatrixService.initService()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            dataStore.clearSessionDatastore()
            Log.d(TAG, "initClient，session无效")
        }
        checkSession()
    }

    suspend fun setOnline() {
        client.setPresence(PresenceState.ONLINE, true)
    }

    suspend fun setOffline() {
        userState.update {
            it.copy(
                isLoggedIn = false
            )
        }
        client.setPresence(PresenceState.OFFLINE, true)
    }

    suspend fun checkSession(): Boolean {
        Log.d(TAG, "===检查session===")
        return try {
            val session = client.session()
            val profile = client.getProfile(session.userId)
            Log.d(TAG, "session.userId profile: ${profile}")
            Log.d(TAG, "session正常")
            userState.update {
                it.copy(
                    isLoggedIn = true,
                    userId = profile.userId,
                    avatarUrl = profile.avatarUrl?:"",
                    displayName = profile.displayName?:""
                )
            }
            setOnline()
            true
        } catch (e: CancellationException) {
            true
        } catch (e: Exception) {
            Log.d(TAG, "session异常:${e}")
            userState.update {
                it.copy(
                    isLoggedIn = false
                )
            }
            false
        }
    }

    suspend fun loginWithPassword(
        username: String,
        password: String,
        initialDeviceName: String,
        deviceId: String,
        saveSession: (
            accessToken: String,
            refreshToken: String,
            userId: String,
            homeserverUrl: String,
            oAuthData: String,
            deviceId: String,
            slidingSyncVersion: String
        ) -> Unit
    ): Boolean {
        client.login(
            username = username,
            password = password,
            initialDeviceName = initialDeviceName,
            deviceId = deviceId
        )
        saveSession(
            client.session().accessToken,
            client.session().refreshToken ?: "",
            client.session().userId,
            client.session().homeserverUrl,
            client.session().oauthData ?: "",
            client.session().deviceId,
            client.session().slidingSyncVersion.name
        )
        checkSession()
//        MatrixService.createRoomServices()
        MatrixService.initService()
        return true
    }

    suspend fun logout() {
        Log.d(TAG, "==开始退出流程==")
        Log.d(TAG, "1.退出登录:client.logout()")
        client.logout()
        Log.d(TAG, "1.关闭observer")
        stopObserver()
        Log.d(TAG, "2.清除datastore和路径文件")
        appDataStore.clearSessionDatastore()
        File(dataPath).deleteRecursively()
        File(cachePath).deleteRecursively()
        Log.d(TAG, "3.销毁client：client.close()")
        client.close()
        Log.d(TAG, "4.检查session状态")
        checkSession()
        Log.d(TAG, "5.重新初始化client")
        initClient(
            initDataPath = dataPath,
            initCachePath = cachePath,
            dataStore = appDataStore
        )
    }

    suspend fun getUserInfo(
        callback: (
            displayName: String,
            avatarUrl: String?,
            userId: String,
            userIdServerName: String,
        ) -> Unit
    ) {
        callback(
            client.displayName(),
            client.avatarUrl(),
            client.userId(),
            client.userIdServerName()
        )

    }

    fun observerProfile() {
        Log.d(TAG, "添加 observerProfile:")
        profileHandle = client.subscribeToOwnProfile(
            object : ProfileListener {
                override fun onUpdate(profile: UserProfile) {
                    Log.d(TAG, "observerProfile: ${profile}")
                    userState.update {
                        it.copy(
                            userId = profile.userId,
                            avatarUrl = profile.avatarUrl?:"",
                            displayName = profile.displayName?:""
                        )
                    }
                }
            }
        )
    }

    fun stopObserver() {
        profileHandle?.cancel()
        profileHandle = null
    }
    suspend fun setUsername(username: String){
        client.setDisplayName(username)
    }
    suspend fun setAvatarUrl(url: String){
        client.setAvatarUrl(url)
    }
    suspend fun uploadAvatar(bytes: ByteArray){
        client.uploadAvatar("image/*",bytes)
        checkSession()
    }
}