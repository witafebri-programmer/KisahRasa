package com.dicoding.kisahrasa.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.kisahrasa.core.data.local.entity.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
