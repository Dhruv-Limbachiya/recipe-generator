package com.dhruvv.recipegenerator.data.api.model

import com.dhruvv.recipegenerator.common.util.toDate
import com.dhruvv.recipegenerator.data.db.entities.RecipeEntity
import com.squareup.moshi.Json

/**
 * Represents a recipe object structured for API communication.
 *
 * @property cuisine The cuisine type of the recipe (e.g., Italian, Mexican). Defaults to an empty string if not specified.
 * @property details Additional details about the recipe, including ingredients, instructions, tips, and variations,
 *                   formatted as plain text. Defaults to an empty string.
 * @property apiIngredients List of ingredients used in the recipe, represented as instances of [ApiIngredient].
 * @property apiInstructions List of cooking instructions for preparing the recipe, represented as instances of [ApiInstruction].
 * @property name The name of the recipe.
 * @property tips List of tips or notes related to the recipe.
 * @property type The type or category of the recipe (e.g., dessert, main course).
 * @property apiVariations List of variations or alternative versions of the recipe, represented as instances of [ApiVariation].
 */
data class ApiRecipe(
    val cuisine: String? = "",
    val details: String? = "",
    @Json(name = "ingredients")
    val apiIngredients: List<ApiIngredient> = mutableListOf(),
    @Json(name = "instructions")
    val apiInstructions: List<ApiInstruction> = mutableListOf(),
    val name: String? = "",
    val tips: List<String> = mutableListOf(),
    val type: String? = "",
    @Json(name = "variations")
    val apiVariations: List<ApiVariation> = mutableListOf(),
) {
    companion object {
        /**
         * Represents an invalid or default instance of [ApiRecipe].
         */
        val INVALID_API_RECIPE = ApiRecipe()
    }
}

/**
 * Converts an instance of [ApiRecipe] to a corresponding [RecipeEntity] object.
 *
 * @return A [RecipeEntity] representing the converted recipe, with an ID of 0 (assuming it's a new entity),
 *         the original [ApiRecipe], and the current timestamp as the generated time.
 */
fun ApiRecipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = 0,
        apiRecipe = this,
        generatedAt = System.currentTimeMillis().toDate(),
    )
}
