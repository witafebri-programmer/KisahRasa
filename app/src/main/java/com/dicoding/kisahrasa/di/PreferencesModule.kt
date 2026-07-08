package com.dicoding.kisahrasa.di

import com.dicoding.kisahrasa.core.data.PreferencesRepositoryImpl
import com.dicoding.kisahrasa.core.domain.repository.IPreferencesRepository
import com.dicoding.kisahrasa.core.domain.usecase.PreferencesInteractor
import com.dicoding.kisahrasa.core.domain.usecase.PreferencesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl,
    ): IPreferencesRepository

    @Binds
    abstract fun bindPreferencesUseCase(
        preferencesInteractor: PreferencesInteractor,
    ): PreferencesUseCase
}
