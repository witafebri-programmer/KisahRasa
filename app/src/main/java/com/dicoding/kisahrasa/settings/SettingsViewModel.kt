package com.dicoding.kisahrasa.settings

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
class SettingsViewModel @Inject constructor(private val preferencesUseCase: PreferencesUseCase) : ViewModel() {

    val themeSetting: StateFlow<Boolean> = preferencesUseCase.getThemeSetting()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferencesUseCase.saveThemeSetting(isDarkModeActive)
        }
    }
}
