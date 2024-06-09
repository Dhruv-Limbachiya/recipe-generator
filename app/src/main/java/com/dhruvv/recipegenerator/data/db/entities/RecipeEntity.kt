package com.dhruvv.recipegenerator.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dhruvv.recipegenerator.data.api.model.Recipe
import com.dhruvv.recipegenerator.data.db.converters.RecipeConverter

// Defines a table named "recipe" in the database with columns for id, recipe, and generatedAt
@Entity(tableName = "recipe")
@TypeConverters(RecipeConverter::class) // Specifies the converter for handling Recipe objects
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
    // Column to store the Recipe object, using the RecipeConverter to convert to/from JSON
    val recipe: Recipe,
    // Column to store the timestamp of when the recipe was generated
    @ColumnInfo(name = "generate_at")
    val generatedAt: String,
)
