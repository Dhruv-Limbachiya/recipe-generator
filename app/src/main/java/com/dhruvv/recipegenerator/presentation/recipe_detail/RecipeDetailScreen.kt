package com.dhruvv.recipegenerator.presentation.recipe_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Int 
) {
    Text(text = "Recipe ID : $recipeId")
}