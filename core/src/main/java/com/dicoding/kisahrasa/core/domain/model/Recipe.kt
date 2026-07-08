package com.dicoding.kisahrasa.core.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val category: String,
    val instructions: String,
    val thumbUrl: String,
    val isFavorite: Boolean
)
