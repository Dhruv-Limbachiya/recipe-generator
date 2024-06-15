package com.dhruvv.recipegenerator.presentation.generate_recipe

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

@Composable
fun GenerateRecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: GenerateRecipeViewModel = hiltViewModel(),
) {

    val staticIngredient = recipeViewModel.staticIngredients

    GenerateRecipeScaffold(modifier, staticIngredient)
}

@Composable
private fun GenerateRecipeScaffold(
    modifier: Modifier = Modifier,
    staticIngredientsMap: Map<String, List<CheckableItem>>,
) {

    val viewModel: GenerateRecipeViewModel = hiltViewModel()

    val generateRecipeState by remember {
        viewModel.generateRecipeState
    }

    Scaffold(
        topBar = {
            GenerateRecipeAppBar(onCloseIconClick = {
                // todo : pop the generate recipe screen
            })
        }
    ) { paddingValues ->
        Box {
            if(generateRecipeState.isLoading) {
                CircularProgressIndicator()
            } else if(generateRecipeState.generatedRecipe != Recipe.INVALID_RECIPE) {
                Toast.makeText(LocalContext.current,"Recipe generated",Toast.LENGTH_LONG).show()
            }

            Column(modifier = Modifier.padding(paddingValues)) {
                Ingredients(modifier = modifier.weight(1f), ingredientsMap = staticIngredientsMap)
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onClick = viewModel::generateRecipe
                ) {
                    Text(text = stringResource(id = R.string.generate_recipe))
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun GenerateRecipePreview() {
    val staticIngredient = GetStaticIngredient().invoke()
    GenerateRecipeScaffold(staticIngredientsMap = staticIngredient)
}
