package com.dhruvv.recipegenerator.presentation.recipe_detail.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruvv.recipegenerator.R
import com.dhruvv.recipegenerator.data.api.model.ApiIngredient

/**
 * A Composable function that displays a list of recipe ingredients.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param ingredients A list of [ApiIngredient] objects representing the ingredients.
 */
@Composable
fun RecipeIngredient(
    modifier: Modifier = Modifier,
    ingredients: List<ApiIngredient>
) {
    Column(modifier = modifier.padding(16.dp)) {
        // Display the "Ingredients" header text
        Text(
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.titleMedium
        )
        // Add spacing between the header and the ingredients list
        Spacer(modifier = Modifier.height(6.dp))
        // Display the list of ingredients
        IngredientText(ingredients = ingredients)
    }
}

/**
 * A Composable function that displays each ingredient in the list.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param ingredients A list of [ApiIngredient] objects representing the ingredients.
 */
@Composable
fun IngredientText(
    modifier: Modifier = Modifier,
    ingredients: List<ApiIngredient>
) {
    // Iterate through each ingredient and display its details
    ingredients.forEach { apiIngredient ->
        // Create a formatted string for each ingredient
        val ingredientString =
            "${apiIngredient.quantity} ${apiIngredient.unit} ${apiIngredient.name}, ${apiIngredient.preparation}"
        // Display the ingredient string with padding at the bottom
        Text(
            text = ingredientString,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

/**
 * A Composable function for previewing the [RecipeIngredient] composable.
 *
 * This function is used only in the Android Studio Preview and not in production.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeIngredientPreview() {
    // Sample list of ingredients for preview purposes
    val previewIngredient = listOf(
        ApiIngredient(
            name = "Potato",
            preparation = "Peeled and cubed",
            quantity = "2",
            unit = "medium"
        ),
        ApiIngredient(
            name = "Onion",
            preparation = "Chopped",
            quantity = "2",
            unit = "medium"
        ),
        ApiIngredient(
            name = "Curd",
            preparation = "Whisk until smooth",
            quantity = "1/2",
            unit = "cup"
        ),
    )
    // Display the RecipeIngredient composable with the sample data
    RecipeIngredient(ingredients = previewIngredient)
}