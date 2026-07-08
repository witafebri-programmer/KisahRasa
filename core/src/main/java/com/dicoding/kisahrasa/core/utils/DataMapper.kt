package com.dicoding.kisahrasa.core.utils

import com.dicoding.kisahrasa.core.data.local.entity.RecipeEntity
import com.dicoding.kisahrasa.core.data.remote.response.RecipeResponse
import com.dicoding.kisahrasa.core.domain.model.Recipe

object DataMapper {
    fun mapResponsesToEntities(input: List<RecipeResponse>): List<RecipeEntity> {
        return input.map {
            RecipeEntity(
                id = it.idMeal,
                name = it.strMeal,
                category = it.strCategory ?: "",
                instructions = it.strInstructions ?: "",
                thumbUrl = it.strMealThumb ?: "",
                isFavorite = false
            )
        }
    }

    fun mapEntitiesToDomain(input: List<RecipeEntity>): List<Recipe> =
        input.map {
            Recipe(
                id = it.id,
                name = it.name,
                category = it.category,
                instructions = it.instructions,
                thumbUrl = it.thumbUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Recipe) = RecipeEntity(
        id = input.id,
        name = input.name,
        category = input.category,
        instructions = input.instructions,
        thumbUrl = input.thumbUrl,
        isFavorite = input.isFavorite
    )
}
