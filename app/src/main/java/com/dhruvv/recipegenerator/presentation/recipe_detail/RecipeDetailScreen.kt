package com.dhruvv.recipegenerator.presentation.recipe_detail

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    onPop: () -> Unit
) {
    RecipeDetailScaffold(modifier = modifier, recipeId = recipeId,onPop = onPop)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScaffold(
    modifier: Modifier = Modifier,
    recipeId: Int,
    recipeDetailViewModel: RecipeDetailViewModel = hiltViewModel(),
    onPop: () -> Unit
) {

    val recipeDetailState by remember {
        recipeDetailViewModel.recipeDetailState
    }

    LaunchedEffect(key1 = Unit) {
       if(recipeId != -1) {
           recipeDetailViewModel.getRecipe(recipeId)
       } else {
           recipeDetailViewModel.resetRecipeState()
       }
    }

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

            if (recipeDetailState.isLoading) {
                Log.i("RecipeDetailScreen", "RecipeDetailScaffold: loading")
                CircularProgressIndicator()
            } else if (recipeDetailState.error.isNotEmpty()) {
                Log.i(
                    "RecipeDetailScreen",
                    "RecipeDetailScaffold: Error : ${recipeDetailState.error}"
                )
                Toast.makeText(LocalContext.current, recipeDetailState.error, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.i(
                    "RecipeDetailScreen",
                    "RecipeDetailScaffold: Recipe : ${recipeDetailState.recipe.apiRecipe.details}"
                )
                recipeDetailState.recipe.apiRecipe.details?.let {
                    Text(
                        it,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(recipeId = -1, onPop = {})
}