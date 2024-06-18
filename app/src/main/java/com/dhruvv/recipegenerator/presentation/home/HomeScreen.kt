package com.dhruvv.recipegenerator.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.dhruvv.recipegenerator.presentation.home.composables.HomeHeader


/**
 * Composable function for displaying the Home screen.
 * It uses HomeScaffold as the main layout container.
 */
@Composable
fun HomeScreen() {
    HomeScaffold()
}

/**
 * Private composable function for the Home screen scaffold.
 * It uses Scaffold to provide basic material design structure.
 */
@Composable
private fun HomeScaffold() {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            HomeHeader()
        }
    }
}

/**
 * Preview function for HomeScaffold composable.
 * It shows the system UI and previews on a Pixel 5 device.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun HomeScreenPreview() {
    HomeScaffold()
}