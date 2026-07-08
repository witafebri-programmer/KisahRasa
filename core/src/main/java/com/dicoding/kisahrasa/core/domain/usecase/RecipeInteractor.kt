package com.dicoding.kisahrasa.core.domain.usecase

import com.dicoding.kisahrasa.core.domain.model.Recipe
import com.dicoding.kisahrasa.core.domain.repository.IRecipeRepository
import com.dicoding.kisahrasa.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeInteractor @Inject constructor(private val recipeRepository: IRecipeRepository) : RecipeUseCase {
    override fun getAllRecipes(query: String): Flow<Resource<List<Recipe>>> =
        recipeRepository.getAllRecipes(query)

    override fun getFavoriteRecipes(): Flow<List<Recipe>> =
        recipeRepository.getFavoriteRecipes()

    override suspend fun setFavoriteRecipe(recipe: Recipe, state: Boolean) =
        recipeRepository.setFavoriteRecipe(recipe, state)
}
