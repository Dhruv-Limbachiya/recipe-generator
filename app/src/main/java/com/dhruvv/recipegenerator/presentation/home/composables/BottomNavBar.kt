package com.dhruvv.recipegenerator.presentation.home.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruvv.recipegenerator.R
import com.dhruvv.recipegenerator.common.navigation.NavItem

//Composable function to create a bottom navigation bar
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier, //Modifier for customizingthe appearance of the navigation bar
    navItems: List<NavItem>, //List of navigation items to display
    onNavItemClick: (NavItem) -> Unit //Callback function triggered when a navigation item is clicked
) {
    //State to track the currently selected item index
    var selectedItem by remember{ mutableIntStateOf(0) }

    //Jetpack Compose NavigationBar composable
    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)), //Clip the top corners of the navigation bar
    ) {
        //Iterate through the list of navigation items
        navItems.forEachIndexed { index, item ->
            //Create a NavigationBarItem for each item
            NavigationBarItem(
                icon = {
                    //Display the icon for the navigation item
                    Icon(painter = item.icon, contentDescription = item.title, modifier.size(24.dp))
                },
                label = {
                    //Display the title of the navigation item
                    Text(item.title)
                },
                selected = selectedItem == index, //Set the selected state based on the current index
                onClick = {
                    //Update the selected item index and trigger the callback function
                    selectedItem = index
                    onNavItemClick(item)
                }
            )
        }
    }
}

//Preview function to display the BottomNavBar composable
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun BottomNavBarPreview() {
    //Sample list of navigation items for the preview
    val items = listOf(
        NavItem(
            title = "Home",
            icon = painterResource(id = R.drawable.ic_home_vector),
            screenRoute = "home",
            selected = true
        ),
        NavItem(
            title = "Recipes",
            icon = painterResource(id = R.drawable.ic_recipes_vector),
            screenRoute = "home",
            selected = true
        ),

        NavItem(
            title = "Saved",
            icon = painterResource(id = R.drawable.ic_filled_saved_vector),
            screenRoute = "home",
            selected = true
        ),
    )

    //Render the BottomNavBar composable with the sample items
    BottomNavBar(navItems = items, onNavItemClick = {})
}
