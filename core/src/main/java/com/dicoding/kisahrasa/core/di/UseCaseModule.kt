package com.dicoding.kisahrasa.core.di

import com.dicoding.kisahrasa.core.domain.usecase.RecipeInteractor
import com.dicoding.kisahrasa.core.domain.usecase.RecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindRecipeUseCase(recipeInteractor: RecipeInteractor): RecipeUseCase
}
