package com.dicoding.kisahrasa.core.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeUI(
    val id: String,
    val name: String,
    val category: String,
    val instructions: String,
    val thumbUrl: String,
    val isFavorite: Boolean
) : Parcelable
