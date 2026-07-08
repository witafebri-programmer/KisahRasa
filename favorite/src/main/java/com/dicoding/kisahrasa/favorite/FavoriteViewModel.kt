package com.dicoding.kisahrasa.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kisahrasa.core.domain.usecase.RecipeUseCase
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.toUI
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(recipeUseCase: RecipeUseCase) : ViewModel() {

    val favoriteRecipes: StateFlow<List<RecipeUI>> = recipeUseCase.getFavoriteRecipes()
        .map { list -> list.map { it.toUI() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
