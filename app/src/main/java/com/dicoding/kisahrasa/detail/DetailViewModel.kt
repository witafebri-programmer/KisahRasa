package com.dicoding.kisahrasa.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kisahrasa.core.domain.usecase.RecipeUseCase
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    fun setFavoriteRecipe(recipe: RecipeUI, newStatus: Boolean) {
        viewModelScope.launch {
            recipeUseCase.setFavoriteRecipe(recipe.toDomain(), newStatus)
        }
    }
}
