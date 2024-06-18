package com.dhruvv.recipegenerator.presentation.home

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.dhruvv.recipegenerator.presentation.home.composables.GetStartedBox
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
            GetStartedBox {

            }
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
    HomeScaffold()
}