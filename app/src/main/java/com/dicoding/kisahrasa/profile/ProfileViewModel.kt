package com.dicoding.kisahrasa.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kisahrasa.core.domain.usecase.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val preferencesUseCase: PreferencesUseCase) : ViewModel() {

    val profileImagePath: StateFlow<String> = preferencesUseCase.getProfileImagePath()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    fun saveProfileImagePath(path: String) {
        viewModelScope.launch {
            preferencesUseCase.saveProfileImagePath(path)
        }
    }

    suspend fun logout() {
        preferencesUseCase.saveLoginSession(false)
    }
}
