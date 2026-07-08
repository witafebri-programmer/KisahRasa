package com.dicoding.kisahrasa.core.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val PROFILE_IMAGE_PATH_KEY = stringPreferencesKey("profile_image_path")
    private val LOGIN_SESSION_KEY = booleanPreferencesKey("is_logged_in")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    fun getProfileImagePath(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PROFILE_IMAGE_PATH_KEY] ?: ""
        }
    }

    suspend fun saveProfileImagePath(path: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_IMAGE_PATH_KEY] = path
        }
    }

    fun getLoginSession(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOGIN_SESSION_KEY] ?: false
        }
    }

    suspend fun saveLoginSession(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_SESSION_KEY] = isLoggedIn
        }
    }
}
