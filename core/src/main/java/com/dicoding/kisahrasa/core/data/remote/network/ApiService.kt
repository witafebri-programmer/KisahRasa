package com.dicoding.kisahrasa.core.data.remote.network

import com.dicoding.kisahrasa.core.data.remote.response.ListRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun getRecipes(
        @Query("s") query: String
    ): ListRecipeResponse
}
