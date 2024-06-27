package com.dhruvv.recipegenerator.presentation.recipes.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruvv.recipegenerator.data.api.model.ApiRecipe
import com.dhruvv.recipegenerator.data.model.Recipe

@Composable
fun RecipesList(
    modifier: Modifier = Modifier,
    recipes: List<Recipe>,
    scrollState: LazyListState,
    showNoOfRecipes: Int = 0,
    onRecipeClicked: (Recipe) -> Unit,
    onRecipeDelete: (Recipe) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (showNoOfRecipes != 0) {
            itemsIndexed(recipes.take(showNoOfRecipes)) { index, item ->
                RecipeItem(
                    recipe = item,
                    onRecipeClicked = onRecipeClicked,
                    onRecipeSwiped = onRecipeDelete,
                )
            }
        } else {
            itemsIndexed(recipes) { index, item ->
                RecipeItem(
                    recipe = item,
                    onRecipeClicked = onRecipeClicked,
                    onRecipeSwiped = onRecipeDelete
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeListPreview() {
    RecipesList(
        recipes = createActualRecipes(),
        scrollState = rememberLazyListState(),
        showNoOfRecipes = 0,
        onRecipeClicked = {},
        onRecipeDelete = {})
}

// Function to create a list of Recipe objects with actual data
fun createActualRecipes(): List<Recipe> {
    val recipe1 = Recipe(
        id = 1,
        apiRecipe = ApiRecipe(
            name = "Spaghetti Carbonara",
            details = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper."
        ),
        generatedAt = "2024-06-25T12:00:00Z",
        isSaved = 1
    )

    val recipe2 = Recipe(
        id = 2,
        apiRecipe = ApiRecipe(
            name = "Chicken Curry",
            details = "A flavorful dish made with tender chicken pieces simmered in a rich, spicy sauce."
        ),
        generatedAt = "2024-06-25T13:00:00Z",
        isSaved = 0
    )

    val recipe3 = Recipe(
        id = 3,
        apiRecipe = ApiRecipe(
            name = "Vegetable Stir Fry",
            details = "A quick and healthy meal made with fresh vegetables stir-fried in a savory sauce."
        ),
        generatedAt = "2024-06-25T14:00:00Z",
        isSaved = 1
    )

    return listOf(recipe1, recipe2, recipe3)
}
