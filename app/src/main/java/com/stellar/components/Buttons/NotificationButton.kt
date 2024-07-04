package com.stellar.components.Buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NotificationButton(navController : NavController){
    IconButton(
        onClick = {
            navController.navigate("Notifications")
            //selectedItem = NavItems.PROFILE_ITEM
        }) {
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = "Notifications"
        )
    }

}