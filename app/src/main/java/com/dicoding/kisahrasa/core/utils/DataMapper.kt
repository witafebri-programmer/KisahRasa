package com.dicoding.kisahrasa.core.utils

import com.dicoding.kisahrasa.core.domain.model.Recipe
import com.dicoding.kisahrasa.core.ui.model.RecipeUI

fun Recipe.toUI(): RecipeUI =
    RecipeUI(
        id = id,
        name = name,
        category = category,
        instructions = instructions,
        thumbUrl = thumbUrl,
        isFavorite = isFavorite
    )

fun RecipeUI.toDomain(): Recipe =
    Recipe(
        id = id,
        name = name,
        category = category,
        instructions = instructions,
        thumbUrl = thumbUrl,
        isFavorite = isFavorite
    )
