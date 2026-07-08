package com.dicoding.kisahrasa.core.data.remote

import com.dicoding.kisahrasa.core.data.remote.network.ApiResponse
import com.dicoding.kisahrasa.core.data.remote.network.ApiService
import com.dicoding.kisahrasa.core.data.remote.response.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getAllRecipes(query: String): Flow<ApiResponse<List<RecipeResponse>>> {
        return flow {
            try {
                val response = apiService.getRecipes(query)
                val dataArray = response.meals
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } else {
                    // API returns {"meals": null} when no results found
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
