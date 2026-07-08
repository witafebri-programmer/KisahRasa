package com.dicoding.kisahrasa.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface PreferencesUseCase {
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
    fun getProfileImagePath(): Flow<String>
    suspend fun saveProfileImagePath(path: String)
    suspend fun saveLoginSession(isLoggedIn: Boolean)
}
