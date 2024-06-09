package com.dhruvv.recipegenerator.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhruvv.recipegenerator.data.db.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    // Inserts a new recipe into the database. If a conflict occurs, the existing record is replaced.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity): Long

    // Retrieves all recipes from the database as a Flow of a list of RecipeEntity objects.
    @Query("SELECT * FROM recipe")
    fun getRecipes(): Flow<List<RecipeEntity>>

    // Retrieves a specific recipe from the database by its id.
    @Query("SELECT * FROM recipe WHERE id=:id")
    suspend fun getRecipeById(id: String): RecipeEntity

    // Deletes a specific recipe from the database and returns the number of rows affected.
    @Delete
    suspend fun deleteExpense(recipeEntity: RecipeEntity): Int
}
