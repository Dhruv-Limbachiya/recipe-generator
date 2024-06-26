package com.dhruvv.recipegenerator.presentation.recipes

import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.presentation.recipe_detail.RecipeDetailState


data class RecipeListState(
    val recipes: List<Recipe> = mutableListOf(),
    val error: String = "",
    val isLoading: Boolean = false,
    val showNoRecipeGenerated: Boolean = true,
    ) {
    companion object {
        val INVALID_RECIPE_LIST_STATE = RecipeDetailState()
    }
}
