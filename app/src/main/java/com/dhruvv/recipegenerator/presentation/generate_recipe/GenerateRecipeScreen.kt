package com.dhruvv.recipegenerator.presentation.generate_recipe

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruvv.recipegenerator.data.model.CheckableItem
import com.dhruvv.recipegenerator.domain.usecases.GetStaticIngredient
import com.dhruvv.recipegenerator.presentation.generate_recipe.composables.GenerateRecipeAppBar
import com.dhruvv.recipegenerator.presentation.generate_recipe.composables.Ingredients

@Composable
fun GenerateRecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: GenerateRecipeViewModel = hiltViewModel(),
) {

    val staticIngredient = recipeViewModel.staticIngredients()

    GenerateRecipeScaffold(modifier, staticIngredient)
}

@Composable
private fun GenerateRecipeScaffold(
    modifier: Modifier = Modifier,
    staticIngredient: MutableMap<String, List<CheckableItem>>
) {
    Scaffold(
        topBar = {
            GenerateRecipeAppBar(onCloseIconClick = {
                // todo : pop the generate recipe screen
            })
        }
    ) { paddingValues ->
        Ingredients(modifier.padding(paddingValues), ingredientsMap = staticIngredient)
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun GenerateRecipePreview() {
    val staticIngredient = GetStaticIngredient().invoke()
    GenerateRecipeScaffold(staticIngredient = staticIngredient)
}
