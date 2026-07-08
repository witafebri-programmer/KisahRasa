package com.dicoding.kisahrasa.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "instructions")
    val instructions: String,

    @ColumnInfo(name = "thumbUrl")
    val thumbUrl: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
