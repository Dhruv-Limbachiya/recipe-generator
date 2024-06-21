package com.dhruvv.recipegenerator.presentation.generate_recipe

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
    val staticIngredient by remember {
        recipeViewModel.staticIngredients
    }

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

        var isAnyItemSelected by remember {
            mutableStateOf(false)
        }

        // Box composable for managing loading state or displaying content
        Box {
            // Column composable to arrange UI elements vertically with padding
            Column(modifier = Modifier.padding(paddingValues)) {

                // Display the list of ingredients with checkboxes
                Ingredients(
                    modifier = modifier.weight(1f).padding(horizontal = 16.dp),
                    ingredientsMap = staticIngredientsMap,
                    onSelectionChange = {
                        isAnyItemSelected =
                            staticIngredientsMap.values.flatten().any { it.isSelected }
                    })


                AnimatedVisibility(visible = isAnyItemSelected) {
                    // Column to arrange the button vertically within the AnimatedVisibility composable
                    Column {
                        // TextButton to initiate the recipe generation process
                        TextButton(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth(),
                            onClick = {
                                viewModel.clearAllSelection()
                                isAnyItemSelected = false
                            }
                        ) {
                            // Row to arrange the icon and text horizontally within the button
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Icon to represent the clear all action
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = stringResource(id = R.string.clear_all),
                                    tint = Color.Red
                                )
                                // Text to accompany the icon
                                Text(
                                    text = stringResource(id = R.string.clear_all),
                                    color = Color.Red,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(16f, TextUnitType.Sp)
                                    )
                                )
                            }
                        }
                    }
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
    GenerateRecipeScaffold(
        staticIngredientsMap = staticIngredient,
        navigateToRecipeDetailScreen = {},
        onPop = {})
}
