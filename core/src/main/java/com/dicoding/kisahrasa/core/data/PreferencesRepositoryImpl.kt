package com.dicoding.kisahrasa.core.data

import com.dicoding.kisahrasa.core.data.local.preferences.AppPreferences
import com.dicoding.kisahrasa.core.domain.repository.IPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences,
) : IPreferencesRepository {
    override fun getThemeSetting(): Flow<Boolean> = appPreferences.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        appPreferences.saveThemeSetting(isDarkModeActive)
    }

    override fun getProfileImagePath(): Flow<String> = appPreferences.getProfileImagePath()

    override suspend fun saveProfileImagePath(path: String) {
        appPreferences.saveProfileImagePath(path)
    }

    override suspend fun saveLoginSession(isLoggedIn: Boolean) {
        appPreferences.saveLoginSession(isLoggedIn)
    }
}
