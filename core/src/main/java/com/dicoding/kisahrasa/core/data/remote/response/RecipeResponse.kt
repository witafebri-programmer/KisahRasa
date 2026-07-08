package com.dicoding.kisahrasa.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListRecipeResponse(
    @SerializedName("meals")
    val meals: List<RecipeResponse>?
)

data class RecipeResponse(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strCategory")
    val strCategory: String?,
    @SerializedName("strInstructions")
    val strInstructions: String?,
    @SerializedName("strMealThumb")
    val strMealThumb: String?
)
