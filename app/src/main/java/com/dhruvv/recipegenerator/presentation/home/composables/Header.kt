package com.dhruvv.recipegenerator.presentation.home.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruvv.recipegenerator.R
import com.dhruvv.recipegenerator.common.util.getMealByTime


// Composable function for displaying the header in Home Screen
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {
    // Display a text element with padding and styled text
    Text(
        modifier = modifier.padding(start = 16.dp, end = 20.dp, top = 16.dp, bottom = 16.dp),
        text = stringResource(id = R.string.home_header, getMealByTime()),
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight(700))
    )
}

// Preview function for the HomeHeader composable
// Shows system UI and specifies the device for preview
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun HomeHeaderPreview() {
    // Calls the HomeHeader composable for preview
    HomeHeader()
}