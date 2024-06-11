package com.dhruvv.recipegenerator.data.api.model

import com.dhruvv.recipegenerator.common.util.toDate
import com.dhruvv.recipegenerator.data.db.entities.RecipeEntity

data class ApiRecipe(
    val cuisine: String? = "",
    val details: String? = "",
    val apiIngredients: List<ApiIngredient> = mutableListOf(),
    val apiInstructions: List<ApiInstruction> = mutableListOf(),
    val name: String? = "",
    val tips: List<String> = mutableListOf(),
    val type: String? = "",
    val apiVariations: List<ApiVariation> = mutableListOf(),
) {
    companion object {
        val INVALID_Api_RECIPE = ApiRecipe()
    }
}

fun ApiRecipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = 0,
        apiRecipe = this,
        generatedAt = System.currentTimeMillis().toDate()
    )
}