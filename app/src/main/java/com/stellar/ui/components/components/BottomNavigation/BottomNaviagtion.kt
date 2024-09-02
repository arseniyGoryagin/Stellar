package com.stellar.components.BottomNavigation

import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stellar.constants.NavItems
import com.stellar.ui.theme.Grey170
import com.stellar.ui.theme.PurpleFont

@Composable
fun BottomNavigationBar(navController: NavController){

    var selectedItem by remember { mutableIntStateOf(NavItems.HOME_ITEM) }

    val navigationItems = listOf("Home", "My Order", "Favorite", "My Profile")

    NavigationBar(
        containerColor = Color.White,
    ) {
        navigationItems.forEachIndexed() { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PurpleFont,
                    selectedTextColor = PurpleFont,
                    unselectedTextColor = Grey170,
                    unselectedIconColor = Grey170
                ),
                selected = if (selectedItem == index) {
                    true
                } else {
                    false
                },
                onClick = {
                    selectedItem = index
                    when (item) {
                        "Home" -> navController.navigate("Home")
                        "My Order" -> navController.navigate("My Order")
                        "Favorite" -> navController.navigate("Favorite")
                        "My Profile" -> navController.navigate("My Profile")
                    }
                },
                icon = {
                    when (item) {
                        "Home" -> Icon(
                            Icons.Outlined.Home,
                            contentDescription = item
                        )

                        "My Order" -> Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = item
                        )

                        "Favorite" -> Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = item
                        )

                        "My Profile" -> Icon(
                            Icons.Outlined.Person,
                            contentDescription = item
                        )
                    }
                },
                label = { Text(item) }
            )
        }
    }


}