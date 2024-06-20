package com.dhruvv.recipegenerator.presentation.generate_recipe

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruvv.recipegenerator.R
import com.dhruvv.recipegenerator.data.model.CheckableItem
import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.domain.usecases.GetStaticIngredient
import com.dhruvv.recipegenerator.presentation.generate_recipe.composables.GenerateRecipeAppBar
import com.dhruvv.recipegenerator.presentation.generate_recipe.composables.Ingredients

/**
 * Composable function for the Generate Recipe screen.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the screen.
 * @param recipeViewModel ViewModel to handle business logic related to generating recipes.
 * @param navigateToRecipeDetailScreen Callback to handle navigation from GenerateRecipeScreen to RecipeDetailScreen.
 * @param onPop Callback function to handle navigation back from the screen.
 */
@Composable
fun GenerateRecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: GenerateRecipeViewModel = hiltViewModel(),
    navigateToRecipeDetailScreen: (Int) -> Unit,
    onPop: () -> Unit
) {
    // Obtain static ingredients from the view model
    val staticIngredient = recipeViewModel.staticIngredients

    // Render the GenerateRecipeScaffold passing necessary parameters
    GenerateRecipeScaffold(
        modifier,
        staticIngredient,
        navigateToRecipeDetailScreen = navigateToRecipeDetailScreen,
        onPop = onPop
    )
}


/**
 * Private composable function for the scaffold of the Generate Recipe screen.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the scaffold.
 * @param staticIngredientsMap Map of static ingredients to be displayed.
 * @param navigateToRecipeDetailScreen Callback to handle navigation from GenerateRecipeScreen to RecipeDetailScreen.
 * @param onPop Callback function to handle navigation back from the screen.
 */
@Composable
private fun GenerateRecipeScaffold(
    modifier: Modifier = Modifier,
    staticIngredientsMap: Map<String, List<CheckableItem>>,
    navigateToRecipeDetailScreen: (Int) -> Unit,
    onPop: () -> Unit
) {
    // Obtain the view model instance for generating recipes
    val viewModel: GenerateRecipeViewModel = hiltViewModel()

    // Observe the state related to recipe generation
    val generateRecipeState by remember {
        viewModel.generateRecipeState
    }

    // Scaffold provides basic material design structure with a top app bar
    Scaffold(
        topBar = {
            // Custom app bar for the Generate Recipe screen
            GenerateRecipeAppBar(onCloseIconClick = onPop)
        }
    ) { paddingValues ->
        // Box composable for managing loading state or displaying content
        Box {
            // Column composable to arrange UI elements vertically with padding
            Column(modifier = Modifier.padding(paddingValues)) {
                // Display the list of ingredients with checkboxes
                Ingredients(modifier = modifier.weight(1f), ingredientsMap = staticIngredientsMap)

                // Button to initiate recipe generation process
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onClick = viewModel::generateRecipe
                ) {
                    Text(text = stringResource(id = R.string.generate_recipe))
                }
            }


            if (generateRecipeState.isLoading) {
                // Show loading indicator if recipe is being generated
                Box(modifier = Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator()
                }
            } else if (generateRecipeState.isErrorMessage.isNotEmpty() && generateRecipeState.generatedRecipe == Recipe.INVALID_RECIPE) {
                Toast.makeText(
                    LocalContext.current,
                    generateRecipeState.isErrorMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else if (generateRecipeState.generatedRecipe != Recipe.INVALID_RECIPE) {
                // navigate to RecipeDetailScreen on successful generation of Recipe
                navigateToRecipeDetailScreen(generateRecipeState.generatedRecipe.id)
                viewModel.resetRecipeDetail()
            }

        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun GenerateRecipePreview() {
    val staticIngredient = GetStaticIngredient().invoke()
    GenerateRecipeScaffold(staticIngredientsMap = staticIngredient, navigateToRecipeDetailScreen = {}, onPop = {})
}
