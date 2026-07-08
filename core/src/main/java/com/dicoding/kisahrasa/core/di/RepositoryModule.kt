package com.dicoding.kisahrasa.core.di

import com.dicoding.kisahrasa.core.data.RecipeRepository
import com.dicoding.kisahrasa.core.domain.repository.IRecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(recipeRepository: RecipeRepository): IRecipeRepository
}
