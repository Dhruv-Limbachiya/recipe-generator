package com.dhruvv.recipegenerator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dhruvv.recipegenerator.data.db.dao.RecipeDao
import com.dhruvv.recipegenerator.data.db.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], exportSchema = false, version = 1)
abstract class RecipeGeneratorDB : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao
    companion object {
        const val RECIPE_GENERATOR_DB = "recipe_generator"
    }
}