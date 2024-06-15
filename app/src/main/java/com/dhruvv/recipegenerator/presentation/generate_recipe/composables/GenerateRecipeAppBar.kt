package com.dhruvv.recipegenerator.presentation.generate_recipe.composables

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruvv.recipegenerator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateRecipeAppBar(
    onCloseIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.choose_your_ingredients)) },
        navigationIcon = {
            IconButton(onClick = onCloseIconClick) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
            }
        },
        windowInsets = WindowInsets(left = 8.dp)
    )
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun GenerateRecipeAppBarPreview() {
    GenerateRecipeAppBar({})
}
