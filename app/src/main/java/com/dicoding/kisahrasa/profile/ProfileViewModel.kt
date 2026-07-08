package com.dicoding.kisahrasa.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kisahrasa.core.domain.usecase.PreferencesUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val profileImagePath: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(private val preferencesUseCase: PreferencesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchUserData()
    }

    fun fetchUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        viewModelScope.launch {
            preferencesUseCase.getProfileImagePath().collect { path ->
                _uiState.update {
                    it.copy(
                        name = user?.displayName ?: "Recipe Enthusiast",
                        email = user?.email ?: "",
                        profileImagePath = path
                    )
                }
            }
        }
    }

    fun saveProfileImagePath(path: String) {
        viewModelScope.launch {
            // Update UI state immediately for instant reactivity
            _uiState.update { it.copy(profileImagePath = path) }
            // Persist to DataStore
            preferencesUseCase.saveProfileImagePath(path)
        }
    }

    suspend fun logout() {
        preferencesUseCase.saveLoginSession(false)
    }
}
