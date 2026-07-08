package com.dicoding.kisahrasa.core.domain.usecase

import com.dicoding.kisahrasa.core.domain.repository.IPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesInteractor @Inject constructor(
    private val repository: IPreferencesRepository,
) : PreferencesUseCase {
    override fun getThemeSetting(): Flow<Boolean> = repository.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        repository.saveThemeSetting(isDarkModeActive)
    }

    override fun getProfileImagePath(): Flow<String> = repository.getProfileImagePath()

    override suspend fun saveProfileImagePath(path: String) {
        repository.saveProfileImagePath(path)
    }

    override suspend fun saveLoginSession(isLoggedIn: Boolean) {
        repository.saveLoginSession(isLoggedIn)
    }
}
