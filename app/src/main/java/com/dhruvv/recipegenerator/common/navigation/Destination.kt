package com.dhruvv.recipegenerator.common.navigation

/**
 * Sealed class representing different destinations in the application.
 *
 * @param route Unique route identifier for each destination.
 */
sealed class Destination(
    val route: String,
) {
    /**
     * Represents the home screen destination.
     */
    data object HomeScreen : Destination(Route.HOME_SCREEN)

    /**
     * Represents the generate recipe screen destination.
     */
    data object GenerateRecipeScreen : Destination(Route.GENERATE_RECIPE)

    /**
     * Represents the recipes screen destination.
     */
    data object RecipeScreen : Destination(Route.RECIPES)
}
