package com.dicoding.kisahrasa.core.domain.usecase

import com.dicoding.kisahrasa.core.domain.model.Recipe
import com.dicoding.kisahrasa.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeUseCase {
    fun getAllRecipes(query: String): Flow<Resource<List<Recipe>>>
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    suspend fun setFavoriteRecipe(recipe: Recipe, state: Boolean)
}
