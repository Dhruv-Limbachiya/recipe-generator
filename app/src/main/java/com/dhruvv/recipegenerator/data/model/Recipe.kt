package com.dhruvv.recipegenerator.data.model

import com.dhruvv.recipegenerator.data.api.model.ApiRecipe

/**
 * Represents a recipe object.
 *
 * @property id The unique identifier of the recipe. Defaults to -1 if not specified.
 * @property apiRecipe The API representation of the recipe. Defaults to [ApiRecipe.INVALID_API_RECIPE].
 * @property generatedAt The timestamp when the recipe was generated. Defaults to an empty string.
 */
data class Recipe(
    val id: Int = -1,
    val apiRecipe: ApiRecipe = ApiRecipe.INVALID_API_RECIPE,
    val generatedAt: String = "",
) {
    companion object {
        /**
         * Represents an invalid or default instance of [Recipe].
         * This can be used, for example, to initialize placeholder or default recipe objects.
         */
        val INVALID_RECIPE = Recipe()
    }
}
