package com.dicoding.kisahrasa.core.data

import com.dicoding.kisahrasa.core.data.local.LocalDataSource
import com.dicoding.kisahrasa.core.data.remote.RemoteDataSource
import com.dicoding.kisahrasa.core.data.remote.network.ApiResponse
import com.dicoding.kisahrasa.core.data.remote.response.RecipeResponse
import com.dicoding.kisahrasa.core.domain.model.Recipe
import com.dicoding.kisahrasa.core.domain.repository.IRecipeRepository
import com.dicoding.kisahrasa.core.utils.AppExecutors
import com.dicoding.kisahrasa.core.utils.DataMapper
import com.dicoding.kisahrasa.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IRecipeRepository {

    override fun getAllRecipes(query: String): Flow<Resource<List<Recipe>>> =
        object : NetworkBoundResource<List<Recipe>, List<RecipeResponse>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return localDataSource.searchRecipes(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<RecipeResponse>>> =
                remoteDataSource.getAllRecipes(query)

            override suspend fun saveCallResult(data: List<RecipeResponse>) {
                val recipeList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRecipes(recipeList)
            }
        }.asFlow()

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return localDataSource.getFavoriteRecipes().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteRecipe(recipe: Recipe, state: Boolean) {
        val recipeEntity = DataMapper.mapDomainToEntity(recipe)
        localDataSource.setFavoriteRecipe(recipeEntity, state)
    }
}
