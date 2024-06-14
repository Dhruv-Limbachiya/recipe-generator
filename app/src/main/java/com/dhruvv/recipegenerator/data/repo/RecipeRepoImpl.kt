package com.dhruvv.recipegenerator.data.repo

import com.dhruvv.recipegenerator.common.util.Resource
import com.dhruvv.recipegenerator.data.api.RecipeGenerator
import com.dhruvv.recipegenerator.data.api.model.toRecipeEntity
import com.dhruvv.recipegenerator.data.db.dao.RecipeDao
import com.dhruvv.recipegenerator.data.db.entities.toRecipeUIModel
import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.domain.repo.RecipeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * RecipeRepoImpl is responsible for providing access to recipe data, including generation and retrieval.
 *
 * @param recipeDao An instance of [RecipeDao] for database access.
 * @param recipeGenerator An instance of [RecipeGenerator] for recipe generation.
 */
class RecipeRepoImpl(
    private val recipeDao: RecipeDao,
    private val recipeGenerator: RecipeGenerator,
) : RecipeRepo {
    /**
     * Generates a recipe based on the provided prompt.
     *
     * @param prompt The prompt to generate the recipe from.
     * @return A flow emitting [Resource] objects representing the state of the recipe generation process.
     */
    override fun generateRecipe(prompt: String): Flow<Resource<Recipe>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = recipeGenerator.generateRecipe(prompt)
                response?.let { apiRecipe ->
                    val recipeEntity = apiRecipe.toRecipeEntity()
                    recipeDao.insertRecipe(recipeEntity)
                    emit(Resource.Success(recipeEntity.toRecipeUIModel()))
                }

                if (response == null) {
                    emit(Resource.Error("Oops! Unable to generate the recipe.😞 Try again!"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    /**
     * Retrieves all recipes from the database.
     *
     * @return A flow emitting [Resource] objects representing the state of the retrieval process.
     */
    override fun getRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val recipes = recipeDao.getRecipes().map { it.toRecipeUIModel() }
                if (recipes.isNotEmpty()) {
                    emit(Resource.Success(recipes))
                } else {
                    emit(Resource.Error("Oops, we couldn't find any recipes!🍽️"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    /**
     * Retrieves a recipe by its ID from the database.
     *
     * @param id The ID of the recipe to retrieve.
     * @return A flow emitting [Resource] objects representing the state of the retrieval process.
     */
    override fun getRecipe(id: Int): Flow<Resource<Recipe>> {
        return flow {
            emit(Resource.Loading())
            try {
                val recipe = recipeDao.getRecipeById(id)?.toRecipeUIModel()
                if (recipe != null) {
                    emit(Resource.Success(recipe))
                } else {
                    emit(Resource.Error("Uh-oh, we couldn't find that recipe!🍲"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}
