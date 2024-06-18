package com.dhruvv.recipegenerator.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.dhruvv.recipegenerator.presentation.common.NoRecipeFound
import com.dhruvv.recipegenerator.presentation.home.composables.GetStartedBox
import com.dhruvv.recipegenerator.presentation.home.composables.HomeHeader


/**
 * Composable function for displaying the Home screen.
 * It uses HomeScaffold as the main layout container.
 *
 * @param navigateToGenerateRecipeScreen Callback to navigate to the Generate Recipe screen.
 */
@Composable
fun HomeScreen(
    navigateToGenerateRecipeScreen: () -> Unit
) {
    // Render the HomeScaffold passing the navigateToGenerateRecipeScreen callback
    HomeScaffold(navigateToGenerateRecipeScreen)
}

/**
 * Private composable function for the Home screen scaffold.
 * It uses Scaffold to provide basic material design structure.
 *
 * @param navigateToGenerateRecipeScreen Callback to navigate to the Generate Recipe screen.
 */
@Composable
private fun HomeScaffold(
    navigateToGenerateRecipeScreen: () -> Unit
) {
    // Scaffold provides a basic material design structure with padding
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Display the header of the Home screen
            HomeHeader()

            // Button to get started and navigate to Generate Recipe screen
            GetStartedBox(onGetStartedButtonClick = navigateToGenerateRecipeScreen)

            // Component to handle scenario when no recipes are found
            NoRecipeFound(
                showAddExpenseButton = true,
                onGenerateRecipeButtonClick = navigateToGenerateRecipeScreen
            )
        }
    }
}

/**
 * Preview function for HomeScaffold composable.
 * It shows the system UI and previews on a Pixel 5 device.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    HomeScaffold({})
}