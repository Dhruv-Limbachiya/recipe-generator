package com.dhruvv.recipegenerator.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dhruvv.recipegenerator.presentation.generate_recipe.GenerateRecipeScreen
import com.dhruvv.recipegenerator.presentation.home.HomeScreen

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
            HomeScreen {
                // Callback to navigate to the GenerateRecipeScreen
                navHostController.navigate(Destination.GenerateRecipeScreen.route)
            }
        }

        // Composable for the GenerateRecipeScreen destination
        composable(route = Destination.GenerateRecipeScreen.route) {
            GenerateRecipeScreen(onPop = {
                // Callback to pop back to the previous screen
                navHostController.popBackStack()
            })
        }
    }
}
