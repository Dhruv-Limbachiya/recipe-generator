package com.dhruvv.recipegenerator.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.dhruvv.recipegenerator.common.navigation.AppNavHost

/**
 * Composable function for the main entry point of the Recipe Generator app.
 * It sets up the main UI structure using Jetpack Compose components.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the main container.
 */
@Composable
fun RecipeGeneratorMain(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        // Surface provides a background color for the entire screen
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            // Scaffold sets up the basic material design structure
            // AppNavHost manages navigation between different screens
            AppNavHost(modifier.padding(innerPadding))
        }
    }
}

/**
 * Preview function for RecipeGeneratorMain composable.
 * Shows how RecipeGeneratorMain appears in different devices and system UI states.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeGeneratorMainPreview() {
    // Render the RecipeGeneratorMain composable in preview mode
    RecipeGeneratorMain()
}
