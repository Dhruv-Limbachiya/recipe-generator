package com.dhruvv.recipegenerator.presentation.recipe_detail

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruvv.recipegenerator.R
import com.dhruvv.recipegenerator.presentation.recipe_detail.composables.RecipeIngredient
import com.dhruvv.recipegenerator.presentation.recipe_detail.composables.RecipeInstruction
import com.dhruvv.recipegenerator.presentation.recipe_detail.composables.RecipeTip
import com.dhruvv.recipegenerator.presentation.recipe_detail.composables.RecipeVariation

/**
 * A Composable function to display the detail screen of a recipe.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param recipeId The ID of the recipe to be displayed.
 * @param onPop A lambda function to be called when the back button is pressed.
 */
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    onPop: () -> Unit
) {
    // Call the scaffold that manages the layout of the screen
    RecipeDetailScaffold(modifier = modifier, recipeId = recipeId, onPop = onPop)
}

/**
 * A Composable function to create a scaffold layout for the recipe detail screen.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param recipeId The ID of the recipe to be displayed.
 * @param onPop A lambda function to be called when the back button is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScaffold(
    modifier: Modifier = Modifier,
    recipeId: Int,
    onPop: () -> Unit
) {
    // Obtain the RecipeDetailViewModel instance using Hilt
    val recipeDetailViewModel: RecipeDetailViewModel = hiltViewModel()

    // Observe the recipe detail state
    val recipeDetailState by remember { recipeDetailViewModel.recipeDetailState }

    // Load recipe details when the composable is launched
    LaunchedEffect(key1 = Unit) {
        if (recipeId != -1) {
            recipeDetailViewModel.getRecipe(recipeId)
        } else {
            recipeDetailViewModel.resetRecipeState()
        }
    }

    // Handle back button press
    BackHandler {
        recipeDetailViewModel.resetRecipeState()
        onPop()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = recipeDetailState.recipe.apiRecipe.name ?: "")
            }, navigationIcon = {
                IconButton(onClick = {
                    recipeDetailViewModel.resetRecipeState()
                    onPop()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                recipeDetailState.isLoading -> {
                    // Show loading indicator while loading
                    CircularProgressIndicator()
                }

                recipeDetailState.error.isNotEmpty() -> {
                    // Show error message if an error occurs
                    Toast.makeText(
                        LocalContext.current,
                        recipeDetailState.error,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {

                    var isSaved by remember {
                        mutableStateOf(false)
                    }

                    if (recipeDetailState.isRecipeSaved) {
                        isSaved = true
                        Toast.makeText(LocalContext.current, "Recipe Saved!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    Box {
                        // Display recipe details in a LazyColumn
                        val recipe = recipeDetailState.recipe.apiRecipe
                        LazyColumn() {
                            item {
                                RecipeIngredient(ingredients = recipe.apiIngredients)
                            }
                            item {
                                if (recipe.apiInstructions.isNotEmpty()) {
                                    RecipeInstruction(instructions = recipe.apiInstructions)
                                }
                            }
                            item {
                                if (recipe.tips.isNotEmpty()) {
                                    RecipeTip(tips = recipe.tips)
                                }
                            }
                            item {
                                if (recipe.apiVariations.isNotEmpty()) {
                                    RecipeVariation(variations = recipe.apiVariations)
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(70.dp))
                            }
                        }

                        Button(modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp), onClick = {
                            recipeDetailViewModel.saveRecipe(recipeId)
                        }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = if (isSaved) R.drawable.ic_filled_saved_vector else R.drawable.ic_save_vector),
                                    contentDescription = "Save Recipe",
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.save_recipe),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(600)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A Composable function for previewing the [RecipeDetailScreen] composable.
 *
 * This function is used only in the Android Studio Preview and not in production.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeDetailScreenPreview() {
    // Display the RecipeDetailScaffold composable with sample data
    RecipeDetailScaffold(recipeId = 1, onPop = {})
}