package com.dhruvv.recipegenerator.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dhruvv.recipegenerator.data.api.model.Recipe
import com.dhruvv.recipegenerator.data.db.converters.RecipeConverter

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
    @TypeConverters(value = [RecipeConverter::class])
    val recipe: Recipe,
    @ColumnInfo(name = "generate_at")
    val generatedAt: String
)