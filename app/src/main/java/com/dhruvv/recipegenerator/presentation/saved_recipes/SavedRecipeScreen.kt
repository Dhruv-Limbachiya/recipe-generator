package com.dhruvv.recipegenerator.presentation.saved_recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruvv.recipegenerator.R
import com.dhruvv.recipegenerator.common.util.recipes
import com.dhruvv.recipegenerator.presentation.common.NoRecipeFound
import com.dhruvv.recipegenerator.presentation.recipes.composables.RecipesList

@Composable
fun SavedRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedRecipeViewModel = hiltViewModel(),
    onBackIconClick: () -> Unit
) {
    val savedRecipesState by remember { viewModel.savedRecipeState }
    SavedRecipeScaffold(
        modifier = modifier,
        savedRecipesState = savedRecipesState,
        onBackIconClick = onBackIconClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRecipeScaffold(
    modifier: Modifier = Modifier,
    savedRecipesState: SavedRecipeState,
    onBackIconClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.all_recipes),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackIconClick) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            when {
                savedRecipesState.showNoRecipeGenerated -> {
                    // Component to handle scenario when no recipes are found
                    NoRecipeFound(
                        showAddExpenseButton = true,
                        onGenerateRecipeButtonClick = { }
                    )
                }

                savedRecipesState.isLoading -> {
                    CircularProgressIndicator()
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        RecipesList(
                            recipes = savedRecipesState.recipes.plus(savedRecipesState.recipes),
                            scrollState = lazyListState,
                            onRecipeClicked = {},
                            onRecipeDelete = {})
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun SavedRecipesScreenPreview() {
    val recipeState =
        SavedRecipeState(recipes = recipes(), isLoading = false, showNoRecipeGenerated = false)
    SavedRecipeScaffold(savedRecipesState = recipeState, onBackIconClick = {})
}