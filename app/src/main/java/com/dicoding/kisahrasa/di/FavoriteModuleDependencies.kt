package com.dicoding.kisahrasa.di

import com.dicoding.kisahrasa.core.domain.usecase.RecipeUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun recipeUseCase(): RecipeUseCase
}
