package com.dicoding.kisahrasa.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
    fun getProfileImagePath(): Flow<String>
    suspend fun saveProfileImagePath(path: String)
    suspend fun saveLoginSession(isLoggedIn: Boolean)
}
