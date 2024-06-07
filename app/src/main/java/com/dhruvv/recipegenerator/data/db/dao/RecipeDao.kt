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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity): Long

    @Query("SELECT * FROM recipe")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM RECIPE WHERE id=:id")
    suspend fun getRecipeById(id: String): RecipeEntity

    @Delete
    suspend fun deleteExpense(recipeEntity: RecipeEntity): Int
}