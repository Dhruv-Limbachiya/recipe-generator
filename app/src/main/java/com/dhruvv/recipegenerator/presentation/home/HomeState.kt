package com.dhruvv.recipegenerator.presentation.home

import com.dhruvv.recipegenerator.data.model.Recipe

/**
 * Represents the state of the Home screen, including generated recipes and visibility flags.
 *
 * @property generatedRecipes The list of generated recipes displayed on the Home screen.
 * @property showNoRecipeGenerated Flag indicating whether to show a message when no recipes are generated.
 */
data class HomeState(
    val generatedRecipes: List<Recipe> = mutableListOf(),
    val showNoRecipeGenerated: Boolean = true
) {
    companion object {
        /**
         * Represents an invalid or default state for HomeState.
         * This instance can be used to initialize HomeState with default values.
         */
        val INVALID_HOME_STATE = HomeState()
    }
}
