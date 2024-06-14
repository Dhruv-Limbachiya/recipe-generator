package com.dhruvv.recipegenerator.presentation.generate_recipe

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GenerateRecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: GenerateRecipeViewModel = hiltViewModel(),
) {
    Scaffold { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val prompt = recipeViewModel.prompt
            val recipe = recipeViewModel.generateRecipeState

            LaunchedEffect(key1 = recipe.value) {
                Log.i("GenerateRecipe", "GenerateRecipeScreen: ${recipe.value.generatedRecipe}")
            }

            BasicPromptTextField(
                prompt = prompt.value,
                onPromptChange = recipeViewModel::updatePrompt,
            )
            Button(onClick = recipeViewModel::generateRecipe) {
                Text(text = "Generate Recipe")
            }
        }
    }
}

@Composable
fun BasicPromptTextField(
    prompt: String,
    onPromptChange: (String) -> Unit,
) {
    TextField(value = prompt, onValueChange = onPromptChange)
}
