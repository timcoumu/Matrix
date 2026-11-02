package com.timcoumu.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.timcoumu.core.data.module.LocalSession
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

class AppDataStore(private val context: Context) {
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    private val USER_ID = stringPreferencesKey("user_id")
    private val HOMESERVER_URL = stringPreferencesKey("homeserver_url")
    private val SLIDING_SYNC_VERSION = stringPreferencesKey("sliding_sync_version")
    private val OAUTH_DATA = stringPreferencesKey("oauth_data")
    private val DEVICE_ID = stringPreferencesKey("device_id")

    private val Context.sessionDataStore by preferencesDataStore(
        name = "user_session"
    )
    private lateinit var cache: LocalSession
    suspend fun preLoad() {
        val pref = context.sessionDataStore.data.first()
        cache = LocalSession(
            accessToken = pref[ACCESS_TOKEN] ?: "",
            refreshToken = pref[REFRESH_TOKEN] ?: "",
            userId = pref[USER_ID] ?: "",
            homeserverUrl = pref[HOMESERVER_URL] ?: "",
            slidingSyncVersion = pref[SLIDING_SYNC_VERSION] ?: "",
            oauthData = pref[OAUTH_DATA] ?: "",
            deviceId = pref[DEVICE_ID] ?: ""
        )
    }

    suspend fun saveSession(
        accessToken: String,
        refreshToken: String,
        userId: String,
        homeserverUrl: String,
        slidingSyncVersion: String,
        oauthData: String,
        deviceId: String
    ) {
        context.sessionDataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
            it[USER_ID] = userId
            it[SLIDING_SYNC_VERSION] = slidingSyncVersion
            it[HOMESERVER_URL] = homeserverUrl
            it[OAUTH_DATA] = oauthData
            it[DEVICE_ID] = deviceId
        }
    }

    fun loadSession(): LocalSession {
        return cache
    }

    suspend fun clearSessionDatastore() {
        context.sessionDataStore.edit {
            it.remove(ACCESS_TOKEN)
            it.remove(REFRESH_TOKEN)
            it.remove(USER_ID)
            it.remove(SLIDING_SYNC_VERSION)
            it.remove(HOMESERVER_URL)
            it.remove(OAUTH_DATA)
            it.remove(DEVICE_ID)
        }
        preLoad()
    }
}