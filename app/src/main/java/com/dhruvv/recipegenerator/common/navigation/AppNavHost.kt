package com.dhruvv.recipegenerator.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dhruvv.recipegenerator.presentation.generate_recipe.GenerateRecipeScreen
import com.dhruvv.recipegenerator.presentation.home.HomeScreen
import com.dhruvv.recipegenerator.presentation.recipe_detail.RecipeDetailScreen
import com.dhruvv.recipegenerator.presentation.recipes.RecipeListScreen
import com.dhruvv.recipegenerator.presentation.saved_recipes.SavedRecipeScreen

/**
 * Composable function representing the navigation host for the entire application.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the navigation host.
 * @param navHostController NavHostController instance to manage navigation within the app.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = Destination.HomeScreen.route,
    ) {
        // Composable for the HomeScreen destination
        composable(route = Destination.HomeScreen.route) {
            HomeScreen(navigateToGenerateRecipeScreen = {
                // Callback to navigate to the GenerateRecipeScreen
                navHostController.navigate(Destination.GenerateRecipeScreen.route)
            }, navigateToRecipeListScreen = { showBackIcon ->
                // Callback to navigate to the RecipeListScreen
                navHostController.navigate("${Destination.RecipeListScreen.route}/?show_back_button=$showBackIcon")
            }, navigateToRecipeDetailScreen = {
                navHostController.navigate("${Destination.RecipeDetailScreen.route}/?id=$it")
            })
        }

        // Composable for the GenerateRecipeScreen destination
        composable(route = Destination.GenerateRecipeScreen.route) {
            GenerateRecipeScreen(navigateToRecipeDetailScreen = { recipeId ->
                // Callback to navigate to the Recipe Detail Screen
                navHostController.navigate("${Destination.RecipeDetailScreen.route}/?id=$recipeId")
            }, onPop = {
                // Callback to pop back to the previous screen
                navHostController.popBackStack()
            })
        }

        // Composable for the RecipeDetailScreen destination with recipeId
        composable(
            // route used to navigate to RecipeDetailScreen passed with recipeId as query parameter
            route = "${Destination.RecipeDetailScreen.route}/?id={recipeId}",
            // composable with "recipeId" as argument
            arguments = listOf(
                navArgument(name = "recipeId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            // Get the recipe id from the arguments and pass to the RecipeDetailScreen composable
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
            RecipeDetailScreen(recipeId = recipeId, onPop = {
                navHostController.popBackStack()
            }, navigateToHomeScreen = {
                navHostController.popBackStack(Destination.HomeScreen.route, inclusive = false)
            })
        }

        // Composable for the RecipeListScreen destination
        composable(
            route = "${Destination.RecipeListScreen.route}/?show_back_button={showBackButton}",
            arguments = listOf(
                navArgument(
                    name = "showBackButton",
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val showBackButton = backStackEntry.arguments?.getBoolean("showBackButton") ?: false
            RecipeListScreen(
                showBackButton = showBackButton,
                onBackIconClick = {
                    navHostController.popBackStack()
                },
                navToRecipeDetail = { recipeId ->
                    navHostController.navigate("${Destination.RecipeDetailScreen.route}/?id=$recipeId")
                },
                navToGenerateRecipeScreen = {
                    navHostController.navigate(Destination.GenerateRecipeScreen.route)
                }
            )
        }

        // Composable for the RecipeListScreen destination
        composable(route = Destination.SaveRecipeListScreen.route) {
            SavedRecipeScreen(
                navToGenerateRecipeScreen = {
                    navHostController.navigate(Destination.GenerateRecipeScreen.route)
                }, onBackIconClick = {
                    navHostController.popBackStack()
                })
        }
    }
}
