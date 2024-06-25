package com.dhruvv.recipegenerator.presentation.recipe_detail

import com.dhruvv.recipegenerator.data.model.Recipe


data class RecipeDetailState(
    val recipe: Recipe = Recipe.INVALID_RECIPE,
    val error: String = "",
    val isLoading: Boolean = false,
    val isRecipeSaved: Boolean = false
) {
    companion object {
        val INVALID_HOME_STATE = RecipeDetailState()
    }
}
