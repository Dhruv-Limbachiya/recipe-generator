package com.dhruvv.recipegenerator.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dhruvv.recipegenerator.data.db.converters.RecipeConverter
import com.dhruvv.recipegenerator.data.db.dao.RecipeDao
import com.dhruvv.recipegenerator.data.db.entities.RecipeEntity

// Defines the Room database for the Recipe Generator application
@Database(entities = [RecipeEntity::class], exportSchema = false, version = 1)
abstract class RecipeGeneratorDB : RoomDatabase() {
    // Abstract method to get the DAO for accessing Recipe data
    abstract fun getRecipeDao(): RecipeDao

    companion object {
        // Constant to define the database name
        const val RECIPE_GENERATOR_DB = "recipe_generator"
    }
}
